package fr.paita.composepractice.utils

import androidx.compose.animation.core.AnimationVector
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import kotlin.math.roundToInt


abstract class AnimationTool <T, V : AnimationVector> {

    abstract fun animationSpec(initialValue: T, targetValue: T): InfiniteRepeatableSpec<T>

    abstract fun setDurationMillis(duration: Int): AnimationTool<T,V>

    @Composable
    fun animateValue(
        initialValue: T,
        targetValue: T,
        typeConverter: TwoWayConverter<T, V>,
        label: String = "AnimationToolTransition"
    ): State<T> {
        val infiniteTransition = rememberInfiniteTransition(label)
        return infiniteTransition.animateValue(
            initialValue = initialValue,
            targetValue = targetValue,
            typeConverter = typeConverter,
            animationSpec = animationSpec(initialValue, targetValue),
        )
    }


    class Glow<T>(private var duration: Int = 1000) : AnimationTool<T, AnimationVector1D>() {
        override fun animationSpec(initialValue: T, targetValue: T): InfiniteRepeatableSpec<T> {
            return infiniteRepeatable(
                animation =
                    keyframes {
                        durationMillis = duration
                        initialValue at 0 // ms
                        targetValue at duration using FastOutLinearInEasing
                    },
                repeatMode = RepeatMode.Reverse
            )
        }

        override fun setDurationMillis(duration: Int): AnimationTool<T, AnimationVector1D> {
            this.duration = duration
            return this
        }
    }

    class Pulse<T>(private var duration: Int = 1000) : AnimationTool<T, AnimationVector1D>() {
        override fun animationSpec(initialValue: T, targetValue: T): InfiniteRepeatableSpec<T> = infiniteRepeatable(
            animation =
                keyframes {
                    durationMillis = duration
                    initialValue at 0 // ms
                    targetValue at (duration*0.2).roundToInt() using FastOutSlowInEasing
                    targetValue at (duration*0.5).roundToInt() using FastOutSlowInEasing
                    targetValue at (duration*0.6).roundToInt() using FastOutSlowInEasing
                    initialValue at (duration*0.65).roundToInt() using FastOutSlowInEasing
                    targetValue at (duration*0.85).roundToInt() using FastOutSlowInEasing
                    initialValue at duration using FastOutLinearInEasing
                },
            repeatMode = RepeatMode.Reverse
        )

        override fun setDurationMillis(duration: Int): AnimationTool<T, AnimationVector1D> {
            this.duration = duration
            return this
        }
    }




    sealed class Animations {
        object Pulse: Animations()
        object Glow: Animations()
    }

}