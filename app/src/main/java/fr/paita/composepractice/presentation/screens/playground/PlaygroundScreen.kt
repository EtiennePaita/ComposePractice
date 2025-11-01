package fr.paita.composepractice.presentation.screens.playground

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import fr.paita.composepractice.ui.theme.Grey
import fr.paita.composepractice.ui.theme.PrimaryAppColor
import fr.paita.composepractice.ui.theme.VeryLightGrey
import fr.paita.composepractice.utils.AnimationTool

@Composable
fun PlaygroundScreen(
    modifier: Modifier = Modifier
) {

    PlaygroundContent(modifier = modifier)
}


@Composable
fun PlaygroundContent(
    modifier: Modifier = Modifier
) {
    var animation by remember { mutableStateOf<AnimationTool.Animations?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(VeryLightGrey)
    ) {

        PlaygroundArea(
            Modifier
                .fillMaxWidth()
                .height(300.dp),
            animationType = animation,
        )
        ControlArea(
            Modifier.fillMaxWidth(),
            animationType = animation,
            onAction = { action ->
                // F*ck *ff this bricolage
                val isSameAction = action == animation
                animation = null
                if (!isSameAction) animation = action
            }
        )
    }
}

@Composable
fun PlaygroundArea(
    modifier: Modifier = Modifier,
    animationType: AnimationTool.Animations?
) {

    val initialValue = remember { 120.dp }
    val expandedValue = remember { 150.dp }
    val duration = remember { 1000 }

    val boxSize = remember { Animatable(initialValue, Dp.VectorConverter) }

    LaunchedEffect(key1 = animationType) {
        boxSize.stop()
        boxSize.snapTo(initialValue)

        if (animationType == null) {
            return@LaunchedEffect
        }

        val animationSpec = when (animationType) {
            AnimationTool.Animations.Glow -> AnimationTool.Glow<Dp>(duration).animationSpec(initialValue, expandedValue)
            AnimationTool.Animations.Pulse -> AnimationTool.Pulse<Dp>(duration).animationSpec(initialValue, expandedValue)
        }

        boxSize.animateTo(
            targetValue = expandedValue,
            animationSpec = animationSpec
        )
    }


    /*val boxSize: Dp by animation?.animateValue(
        initialValue = 120.dp,
        targetValue = 150.dp,
        typeConverter = Dp.VectorConverter,
        label = "BoxSizeAnimation"
    ) ?: remember { mutableStateOf(120.dp) }*/

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.size(boxSize.value),
            colors = CardDefaults.cardColors(containerColor = Grey),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        ) { }
    }
}

@Composable
fun ControlArea(
    modifier: Modifier = Modifier,
    animationType: AnimationTool.Animations?,
    onAction: (AnimationTool.Animations) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
        ) {
            Button(
                onClick = {
                    onAction.invoke(AnimationTool.Animations.Glow)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = when(animationType) {
                        AnimationTool.Animations.Glow -> PrimaryAppColor
                        else -> Grey
                    }
                )
            ) {
                Text("Glow")
            }

            Spacer(Modifier.width(30.dp))

            Button(
                onClick = {
                    onAction.invoke(AnimationTool.Animations.Pulse)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = when(animationType) {
                        AnimationTool.Animations.Pulse -> PrimaryAppColor
                        else -> Grey
                    }
                )
            ) {
                Text("Pulse")
            }
        }
    }
}

@Preview
@Composable
private fun PlaygroundArea_Preview() {
    PlaygroundArea(
        animationType = null
    )
}

@Preview
@Composable
private fun ControlArea_Preview() {
    ControlArea(
        onAction = {},
        animationType = null
    )
}

@Preview
@Composable
private fun PlaygroundContent_Preview() {
    PlaygroundContent()
}