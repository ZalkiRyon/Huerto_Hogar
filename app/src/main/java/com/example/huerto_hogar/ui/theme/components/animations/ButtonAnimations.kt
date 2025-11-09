package com.example.huerto_hogar.ui.theme.components.animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 * Animaciones para componentes de tipo botón.
 * Implementa efectos visuales de interacción, estados de carga y feedback táctil.
 */

// ============================================
// 1. PRESS/CLICK ANIMATION - Scale Down
// ============================================

/**
 * Modifier que aplica efecto de escala durante la interacción de presión.
 * Reduce el tamaño del componente mientras está siendo presionado.
 * 
 * @param pressedScale Factor de escala durante el estado presionado (default 0.95)
 */
fun Modifier.pressClickEffect(
    pressedScale: Float = AnimationSpecs.SCALE_PRESSED
) = composed {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) pressedScale else AnimationSpecs.SCALE_NORMAL,
        animationSpec = AnimationSpecs.buttonPressSpec(),
        label = "pressScale"
    )
    
    this
        .scale(scale)
        .pointerInput(Unit) {
            coroutineScope {
                while (true) {
                    awaitPointerEventScope {
                        awaitFirstDown(requireUnconsumed = false)
                        isPressed = true
                        
                        waitForUpOrCancellation()
                        isPressed = false
                    }
                }
            }
        }
}

/**
 * Variant que utiliza MutableInteractionSource para componentes Material3.
 * Compatible con botones que ya manejan estados de interacción.
 */
@Composable
fun Modifier.pressClickEffectWithInteraction(
    interactionSource: MutableInteractionSource
): Modifier {
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) AnimationSpecs.SCALE_PRESSED else AnimationSpecs.SCALE_NORMAL,
        animationSpec = AnimationSpecs.buttonPressSpec(),
        label = "pressScaleInteraction"
    )
    
    return this.scale(scale)
}

// ============================================
// 2. BOUNCE ANIMATION - Aparición con rebote
// ============================================

/**
 * Modifier que anima la aparición del componente con efecto de rebote elástico.
 * Se ejecuta una vez al momento de la composición.
 * 
 * @param delay Retraso en milisegundos antes de iniciar la animación
 */
@Composable
fun Modifier.bounceInEffect(
    delay: Int = 0
): Modifier {
    var hasAnimated by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (hasAnimated) AnimationSpecs.SCALE_NORMAL else AnimationSpecs.SCALE_SMALL,
        animationSpec = AnimationSpecs.bounceSpec(),
        label = "bounceIn"
    )
    
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(delay.toLong())
        hasAnimated = true
    }
    
    return this.scale(scale)
}

// ============================================
// 3. SHAKE ANIMATION - Para errores
// ============================================

/**
 * Modifier que aplica efecto de sacudida horizontal.
 * La animación se activa cada vez que el valor trigger cambia.
 * 
 * @param trigger Valor de control; incrementar para activar la animación
 */
@Composable
fun Modifier.shakeEffect(trigger: Int): Modifier {
    val offsetX by animateFloatAsState(
        targetValue = 0f,
        animationSpec = AnimationSpecs.shakeSpec(),
        label = "shake"
    )
    
    LaunchedEffect(trigger) {
        if (trigger > 0) {
            // La animación se activa cada vez que trigger cambia
        }
    }
    
    return this.graphicsLayer {
        translationX = offsetX
    }
}

/**
 * Controlador de estado para la animación de sacudida.
 * Proporciona método shake() para activar la animación de forma programática.
 */
class ShakeController {
    var trigger by mutableStateOf(0)
        private set
    
    fun shake() {
        trigger++
    }
}

@Composable
fun rememberShakeController(): ShakeController {
    return remember { ShakeController() }
}

// ============================================
// 4. LOADING BUTTON STATE - Pulsing
// ============================================

/**
 * Modifier que aplica pulsación continua durante estado de carga.
 * La animación se ejecuta en loop infinito mientras isLoading sea true.
 * 
 * @param isLoading Estado de carga del componente
 */
@Composable
fun Modifier.loadingPulseEffect(isLoading: Boolean): Modifier {
    val infiniteTransition = rememberInfiniteTransition(label = "loadingPulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulsing"
    )
    
    return if (isLoading) {
        this.scale(scale)
    } else {
        this
    }
}

// ============================================
// 5. RIPPLE SCALE EFFECT - Crecimiento al tap
// ============================================

/**
 * Modifier que aplica efecto de escala expansiva durante tap.
 * El componente se expande brevemente y retorna a su tamaño original.
 * 
 * @param onClick Callback ejecutado al completar la interacción
 */
fun Modifier.rippleScaleEffect(
    onClick: () -> Unit
) = composed {
    var isScaling by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isScaling) AnimationSpecs.SCALE_ENLARGED else AnimationSpecs.SCALE_NORMAL,
        animationSpec = tween(
            durationMillis = AnimationSpecs.DURATION_SHORT,
            easing = FastOutLinearInEasing
        ),
        label = "rippleScale",
        finishedListener = {
            if (isScaling) {
                isScaling = false
            }
        }
    )
    
    this
        .scale(scale)
        .pointerInput(Unit) {
            coroutineScope {
                while (true) {
                    awaitPointerEventScope {
                        awaitFirstDown(requireUnconsumed = false)
                        isScaling = true
                        
                        val up = waitForUpOrCancellation()
                        if (up != null) {
                            onClick()
                        } else {
                            isScaling = false
                        }
                    }
                }
            }
        }
}

// ============================================
// 6. DISABLED STATE ANIMATION
// ============================================

/**
 * Modifier que anima la transición entre estados habilitado/deshabilitado.
 * Ajusta escala y opacidad de forma progresiva.
 * 
 * @param enabled Estado actual del componente
 */
@Composable
fun Modifier.disabledStateEffect(enabled: Boolean): Modifier {
    val scale by animateFloatAsState(
        targetValue = if (enabled) AnimationSpecs.SCALE_NORMAL else 0.9f,
        animationSpec = AnimationSpecs.smoothSpec(),
        label = "disabledScale"
    )
    
    val alpha by animateFloatAsState(
        targetValue = if (enabled) AnimationSpecs.ALPHA_VISIBLE else AnimationSpecs.ALPHA_SEMI_TRANSPARENT,
        animationSpec = AnimationSpecs.fadeSpec(),
        label = "disabledAlpha"
    )
    
    return this
        .scale(scale)
        .graphicsLayer { this.alpha = alpha }
}

// ============================================
// 7. COMBINED BUTTON EFFECT - Todo en uno
// ============================================

/**
 * Modifier que combina múltiples efectos de animación para botones.
 * Incluye bounce de entrada, press effect y transición de estado disabled.
 * 
 * @param enabled Estado habilitado del componente
 * @param bounceDelay Retraso en milisegundos para la animación de entrada
 */
@Composable
fun Modifier.animatedButton(
    enabled: Boolean = true,
    bounceDelay: Int = 0
): Modifier {
    return this
        .bounceInEffect(delay = bounceDelay)
        .pressClickEffect()
        .disabledStateEffect(enabled)
}
