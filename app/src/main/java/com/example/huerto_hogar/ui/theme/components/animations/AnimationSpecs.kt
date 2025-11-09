package com.example.huerto_hogar.ui.theme.components.animations

import androidx.compose.animation.core.*
import androidx.compose.ui.unit.dp

/**
 * Especificaciones de animación globales.
 * Define constantes y configuraciones reutilizables para mantener consistencia
 * en duraciones, delays y funciones de easing a través de toda la aplicación.
 */
object AnimationSpecs {
    
    // ============================================
    // DURACIONES (en milisegundos)
    // ============================================
    const val DURATION_INSTANT = 100
    const val DURATION_SHORT = 200
    const val DURATION_MEDIUM = 300
    const val DURATION_LONG = 500
    const val DURATION_EXTRA_LONG = 800
    
    // ============================================
    // DELAYS
    // ============================================
    const val DELAY_SHORT = 50
    const val DELAY_MEDIUM = 100
    const val DELAY_LONG = 150
    
    // ============================================
    // VALORES DE ESCALA
    // ============================================
    const val SCALE_PRESSED = 0.95f
    const val SCALE_NORMAL = 1f
    const val SCALE_ENLARGED = 1.1f
    const val SCALE_SMALL = 0.8f
    
    // ============================================
    // VALORES DE ALPHA
    // ============================================
    const val ALPHA_INVISIBLE = 0f
    const val ALPHA_SEMI_TRANSPARENT = 0.5f
    const val ALPHA_VISIBLE = 1f
    
    // ============================================
    // OFFSETS Y DESPLAZAMIENTOS
    // ============================================
    val OFFSET_SMALL = 8.dp
    val OFFSET_MEDIUM = 16.dp
    val OFFSET_LARGE = 32.dp
    
    // ============================================
    // EASING FUNCTIONS (Curvas de animación)
    // ============================================
    val EaseInOutQuad = CubicBezierEasing(0.45f, 0.05f, 0.55f, 0.95f)
    val EaseOutBack = CubicBezierEasing(0.34f, 1.56f, 0.64f, 1f)
    val EaseInBack = CubicBezierEasing(0.36f, 0f, 0.66f, -0.56f)
    val EaseOutElastic = CubicBezierEasing(0.68f, -0.55f, 0.265f, 1.55f)
    
    // ============================================
    // SPECS PRE-CONFIGURADAS
    // ============================================
    
    /** Especificación optimizada para interacciones de botones con respuesta rápida */
    fun <T> buttonPressSpec(): FiniteAnimationSpec<T> = tween(
        durationMillis = DURATION_INSTANT,
        easing = FastOutSlowInEasing
    )
    
    /** Especificación para transiciones entre pantallas con movimiento suave */
    fun <T> screenTransitionSpec(): FiniteAnimationSpec<T> = tween(
        durationMillis = DURATION_MEDIUM,
        easing = EaseInOutQuad
    )
    
    /** Especificación con efecto de rebote elástico */
    fun <T> bounceSpec(): FiniteAnimationSpec<T> = spring(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessMediumLow
    )
    
    /** Especificación de movimiento suave sin rebote */
    fun <T> smoothSpec(): FiniteAnimationSpec<T> = spring(
        dampingRatio = Spring.DampingRatioNoBouncy,
        stiffness = Spring.StiffnessMedium
    )
    
    /** Especificación de sacudida horizontal con keyframes progresivos */
    fun shakeSpec(): FiniteAnimationSpec<Float> = keyframes {
        durationMillis = DURATION_LONG
        0f at 0
        -10f at 50
        10f at 100
        -10f at 150
        10f at 200
        -5f at 250
        5f at 300
        0f at 400
    }
    
    /** Especificación lineal para transiciones de opacidad */
    fun <T> fadeSpec(): FiniteAnimationSpec<T> = tween(
        durationMillis = DURATION_SHORT,
        easing = LinearEasing
    )
    
    /** Especificación para animaciones de entrada con easing de retroceso */
    fun <T> enterSpec(): FiniteAnimationSpec<T> = tween(
        durationMillis = DURATION_MEDIUM,
        easing = EaseOutBack
    )
    
    /** Especificación para animaciones de salida acelerada */
    fun <T> exitSpec(): FiniteAnimationSpec<T> = tween(
        durationMillis = DURATION_SHORT,
        easing = FastOutLinearInEasing
    )
    
    /** Especificación infinita para indicadores de carga continuos */
    fun infiniteLoadingSpec(): InfiniteRepeatableSpec<Float> = infiniteRepeatable(
        animation = tween(
            durationMillis = DURATION_EXTRA_LONG,
            easing = LinearEasing
        ),
        repeatMode = RepeatMode.Restart
    )
    
    /** Especificación infinita para efectos de pulsación */
    fun infinitePulseSpec(): InfiniteRepeatableSpec<Float> = infiniteRepeatable(
        animation = tween(
            durationMillis = DURATION_LONG,
            easing = FastOutSlowInEasing
        ),
        repeatMode = RepeatMode.Reverse
    )
}
