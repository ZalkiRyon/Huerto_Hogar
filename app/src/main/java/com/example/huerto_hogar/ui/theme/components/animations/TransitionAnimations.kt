package com.example.huerto_hogar.ui.theme.components.animations

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavBackStackEntry
import kotlin.math.roundToInt

/**
 * Transiciones animadas para navegación entre pantallas.
 * Proporciona efectos de entrada y salida compatibles con Navigation Compose.
 */

// ============================================
// 1. SLIDE TRANSITIONS - Horizontal & Vertical
// ============================================

/**
 * Transición de entrada con desplazamiento desde el borde derecho.
 */
fun slideInFromRight(): EnterTransition {
    return slideInHorizontally(
        initialOffsetX = { fullWidth -> fullWidth },
        animationSpec = tween(
            durationMillis = AnimationSpecs.DURATION_MEDIUM,
            easing = AnimationSpecs.EaseInOutQuad
        )
    )
}

fun slideInFromLeft(): EnterTransition {
    return slideInHorizontally(
        initialOffsetX = { fullWidth -> -fullWidth },
        animationSpec = tween(
            durationMillis = AnimationSpecs.DURATION_MEDIUM,
            easing = AnimationSpecs.EaseInOutQuad
        )
    )
}

fun slideOutToRight(): ExitTransition {
    return slideOutHorizontally(
        targetOffsetX = { fullWidth -> fullWidth },
        animationSpec = tween(
            durationMillis = AnimationSpecs.DURATION_MEDIUM,
            easing = AnimationSpecs.EaseInOutQuad
        )
    )
}

fun slideOutToLeft(): ExitTransition {
    return slideOutHorizontally(
        targetOffsetX = { fullWidth -> -fullWidth },
        animationSpec = tween(
            durationMillis = AnimationSpecs.DURATION_MEDIUM,
            easing = AnimationSpecs.EaseInOutQuad
        )
    )
}

/**
 * Transición de entrada con desplazamiento vertical desde abajo.
 */
fun slideInFromBottom(): EnterTransition {
    return slideInVertically(
        initialOffsetY = { fullHeight -> fullHeight },
        animationSpec = tween(
            durationMillis = AnimationSpecs.DURATION_MEDIUM,
            easing = AnimationSpecs.EaseInOutQuad
        )
    )
}

fun slideInFromTop(): EnterTransition {
    return slideInVertically(
        initialOffsetY = { fullHeight -> -fullHeight },
        animationSpec = tween(
            durationMillis = AnimationSpecs.DURATION_MEDIUM,
            easing = AnimationSpecs.EaseInOutQuad
        )
    )
}

fun slideOutToBottom(): ExitTransition {
    return slideOutVertically(
        targetOffsetY = { fullHeight -> fullHeight },
        animationSpec = tween(
            durationMillis = AnimationSpecs.DURATION_MEDIUM,
            easing = AnimationSpecs.EaseInOutQuad
        )
    )
}

fun slideOutToTop(): ExitTransition {
    return slideOutVertically(
        targetOffsetY = { fullHeight -> -fullHeight },
        animationSpec = tween(
            durationMillis = AnimationSpecs.DURATION_MEDIUM,
            easing = AnimationSpecs.EaseInOutQuad
        )
    )
}

// ============================================
// 2. FADE TRANSITIONS - Aparición/Desaparición
// ============================================

/**
 * Transición de entrada con fade lineal.
 */
fun fadeIn(): EnterTransition {
    return fadeIn(
        animationSpec = tween(
            durationMillis = AnimationSpecs.DURATION_SHORT,
            easing = LinearEasing
        )
    )
}

fun fadeOut(): ExitTransition {
    return fadeOut(
        animationSpec = tween(
            durationMillis = AnimationSpecs.DURATION_SHORT,
            easing = LinearEasing
        )
    )
}

// ============================================
// 3. SCALE + FADE - Para Login/Home
// ============================================

/**
 * Transición combinada de escala y fade para entradas destacadas.
 */
fun scaleInWithFade(): EnterTransition {
    return scaleIn(
        initialScale = 0.8f,
        animationSpec = tween(
            durationMillis = AnimationSpecs.DURATION_MEDIUM,
            easing = AnimationSpecs.EaseOutBack
        )
    ) + fadeIn(
        animationSpec = tween(
            durationMillis = AnimationSpecs.DURATION_MEDIUM
        )
    )
}

fun scaleOutWithFade(): ExitTransition {
    return scaleOut(
        targetScale = 0.8f,
        animationSpec = tween(
            durationMillis = AnimationSpecs.DURATION_SHORT,
            easing = FastOutLinearInEasing
        )
    ) + fadeOut(
        animationSpec = tween(
            durationMillis = AnimationSpecs.DURATION_SHORT
        )
    )
}

// ============================================
// 4. SLIDE + FADE COMBINED - Más suave
// ============================================

/**
 * Transición combinada de desplazamiento horizontal con fade.
 */
fun slideInFromRightWithFade(): EnterTransition {
    return slideInFromRight() + fadeIn()
}

fun slideInFromLeftWithFade(): EnterTransition {
    return slideInFromLeft() + fadeIn()
}

fun slideOutToRightWithFade(): ExitTransition {
    return slideOutToRight() + fadeOut()
}

fun slideOutToLeftWithFade(): ExitTransition {
    return slideOutToLeft() + fadeOut()
}

/**
 * Transición combinada de desplazamiento vertical con fade.
 */
fun slideInFromBottomWithFade(): EnterTransition {
    return slideInFromBottom() + fadeIn()
}

fun slideOutToBottomWithFade(): ExitTransition {
    return slideOutToBottom() + fadeOut()
}

// ============================================
// 5. NAVIGATION DIRECTION HELPER
// ============================================

/**
 * Determina dirección de transición basada en índices de navegación.
 * Selecciona automáticamente slide derecha/izquierda según dirección.
 * 
 * @param fromIndex Índice de origen
 * @param toIndex Índice de destino
 * @return Par de transiciones (entrada, salida)
 */
fun getNavigationTransition(
    fromIndex: Int,
    toIndex: Int
): Pair<EnterTransition, ExitTransition> {
    return if (toIndex > fromIndex) {
        // Navegando hacia adelante (derecha)
        Pair(slideInFromRightWithFade(), slideOutToLeftWithFade())
    } else {
        // Navegando hacia atrás (izquierda)
        Pair(slideInFromLeftWithFade(), slideOutToRightWithFade())
    }
}

// ============================================
// 6. SCREEN TYPE TRANSITIONS - Por tipo de pantalla
// ============================================

/**
 * Tipos de transición predefinidos según contexto de navegación.
 */
enum class ScreenTransitionType {
    HORIZONTAL_SLIDE,      // Home ↔ Cart ↔ Fav
    VERTICAL_SLIDE,        // Modals, Drawer
    FADE,                  // Pantallas secundarias
    SCALE_FADE,            // Transiciones importantes
    NONE                   // Sin animación
}

/**
 * Retorna par de transiciones según tipo de pantalla y dirección.
 * 
 * @param type Tipo de transición requerida
 * @param isForward Dirección de navegación (adelante/atrás)
 * @return Par de transiciones (entrada, salida)
 */
fun getTransitionForScreenType(
    type: ScreenTransitionType,
    isForward: Boolean = true
): Pair<EnterTransition, ExitTransition> {
    return when (type) {
        ScreenTransitionType.HORIZONTAL_SLIDE -> {
            if (isForward) {
                Pair(slideInFromRightWithFade(), slideOutToLeftWithFade())
            } else {
                Pair(slideInFromLeftWithFade(), slideOutToRightWithFade())
            }
        }
        ScreenTransitionType.VERTICAL_SLIDE -> {
            Pair(slideInFromBottomWithFade(), slideOutToBottomWithFade())
        }
        ScreenTransitionType.FADE -> {
            Pair(fadeIn(), fadeOut())
        }
        ScreenTransitionType.SCALE_FADE -> {
            Pair(scaleInWithFade(), scaleOutWithFade())
        }
        ScreenTransitionType.NONE -> {
            Pair(EnterTransition.None, ExitTransition.None)
        }
    }
}

// ============================================
// 7. COMPOSABLE TRANSITION ANIMATIONS
// ============================================

/**
 * Composable que aplica transición de fade a su contenido.
 * 
 * @param visible Control de visibilidad del contenido
 * @param durationMillis Duración de la transición en milisegundos
 * @param content Contenido a animar
 */
@Composable
fun FadeTransition(
    visible: Boolean,
    durationMillis: Int = AnimationSpecs.DURATION_SHORT,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(durationMillis)),
        exit = fadeOut(animationSpec = tween(durationMillis))
    ) {
        content()
    }
}

/**
 * Composable que aplica transición de slide vertical con fade.
 * 
 * @param visible Control de visibilidad del contenido
 * @param content Contenido a animar
 */
@Composable
fun SlideFromBottomTransition(
    visible: Boolean,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInFromBottomWithFade(),
        exit = slideOutToBottomWithFade()
    ) {
        content()
    }
}

/**
 * Composable que aplica transición de escala con fade.
 * 
 * @param visible Control de visibilidad del contenido
 * @param content Contenido a animar
 */
@Composable
fun ScaleTransition(
    visible: Boolean,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = scaleInWithFade(),
        exit = scaleOutWithFade()
    ) {
        content()
    }
}

// ============================================
// 8. CROSSFADE - Transición suave entre contenidos
// ============================================

/**
 * Composable que aplica crossfade entre diferentes estados.
 * 
 * @param targetState Estado que controla el contenido mostrado
 * @param durationMillis Duración de la transición
 * @param content Lambda que renderiza contenido según el estado
 */
@Composable
fun <T> CustomCrossfade(
    targetState: T,
    durationMillis: Int = AnimationSpecs.DURATION_MEDIUM,
    content: @Composable (T) -> Unit
) {
    Crossfade(
        targetState = targetState,
        animationSpec = tween(durationMillis = durationMillis),
        label = "customCrossfade"
    ) { state ->
        content(state)
    }
}

// ============================================
// 9. OFFSET ANIMATION - Para elementos individuales
// ============================================

/**
 * Modifier que anima desplazamiento vertical y opacidad.
 * 
 * @param visible Estado de visibilidad del elemento
 * @param offsetY Distancia de desplazamiento inicial en píxeles
 */
@Composable
fun Modifier.offsetAnimation(
    visible: Boolean,
    offsetY: Float = 300f
): Modifier {
    val offset by animateFloatAsState(
        targetValue = if (visible) 0f else offsetY,
        animationSpec = tween(
            durationMillis = AnimationSpecs.DURATION_MEDIUM,
            easing = AnimationSpecs.EaseInOutQuad
        ),
        label = "offsetAnimation"
    )
    
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = AnimationSpecs.DURATION_SHORT),
        label = "alphaAnimation"
    )
    
    return this
        .offset { IntOffset(0, offset.roundToInt()) }
        .alpha(alpha)
}
