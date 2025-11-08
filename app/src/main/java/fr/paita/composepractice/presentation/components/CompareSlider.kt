package fr.paita.composepractice.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DragHandle
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderState
import androidx.compose.material3.rememberSliderState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import fr.paita.composepractice.ui.theme.ComposePracticeTheme
import fr.paita.composepractice.ui.theme.Orange
import fr.paita.composepractice.ui.theme.RedError




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntegratedCompareSlider(
    modifier: Modifier = Modifier,
    initialContent: @Composable (Modifier) -> Unit,
    compareContent: @Composable (Modifier) -> Unit,
) {
    val sliderState = rememberSliderState(0.2f)
    val sliderThumbWidth = remember { 40.dp }

    Box(
        modifier = modifier
    ) {
        initialContent(modifier.padding(horizontal = sliderThumbWidth/2))
        compareContent(
            modifier
                .padding(horizontal = sliderThumbWidth/2)
                .graphicsLayer {
                    shape = object : Shape {
                        override fun createOutline(
                            size: Size,
                            layoutDirection: LayoutDirection,
                            density: Density
                        ): Outline {
                            val rect = Rect(
                                left = 0f,
                                top = 0f,
                                right = size.width * sliderState.value,
                                bottom = size.height
                            )
                            return Outline.Rectangle(rect)
                        }
                    }
                    clip = true
                },
        )

        Slider(
            sliderState,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            thumb = { state ->
                Box(
                    Modifier
                        .background(
                            color = Color.White,
                            shape = CircleShape
                        )
                        .animateContentSize()
                        .size(
                            size = if (state.isDragging)
                                sliderThumbWidth.plus(10.dp)
                            else
                                sliderThumbWidth
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.DragHandle,
                        contentDescription = "Drag area icon",
                        modifier = Modifier.rotate(90f),
                        tint = Color.Black.copy(alpha = 0.8f)
                    )
                }},
            track = { state ->
                VerticalSliderTrack(
                    Modifier,
                    state,
                ) },
        )
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompareSlider(
    modifier: Modifier = Modifier,
    initialContent: @Composable (Modifier) -> Unit,
    compareContent: @Composable (Modifier) -> Unit,
) {
    val sliderState = rememberSliderState(0.2f)

    Column(Modifier.fillMaxWidth()) {

        Box(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            initialContent(modifier)
            compareContent(
                modifier
                    .graphicsLayer {
                        shape = object : Shape {
                            override fun createOutline(
                                size: Size,
                                layoutDirection: LayoutDirection,
                                density: Density
                            ): Outline {
                                val rect = Rect(
                                    left = 0f,
                                    top = 0f,
                                    right = size.width * sliderState.value,
                                    bottom = size.height
                                )
                                return Outline.Rectangle(rect)
                            }
                        }
                        clip = true
                    },
            )
        }

        Slider(
            sliderState,
            modifier = Modifier.fillMaxWidth(),
            thumb = { state ->
                CustomSliderThumb(
                    Modifier
                        .background(
                            color = lerp(RedError, Orange, state.value),
                            shape = CircleShape
                        )
                ) },
            track = { state -> CustomSliderTrack(state, Modifier) },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSliderThumb(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier
            .size(30.dp)
    )
}


/**
 * Use of .weight to create a translation effect
 *
 * @param sliderState
 * @param modifier
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSliderTrack(
    sliderState: SliderState,
    modifier: Modifier
) {

    Row(
        modifier = modifier
    ) {
        Box(
            Modifier
                .weight(
                    sliderState.value.let { v ->
                        when {
                            (v < 0.1f) -> 0.05f
                            (v > 0.9f) -> 0.95f
                            else -> v
                        }
                    }
                )
                .background(
                    color = Orange,
                    shape = RoundedCornerShape(8.dp)
                )
                .height(8.dp)
        )
        Box(
            Modifier
                .weight(
                    (1f - sliderState.value).let { v ->
                        when {
                            (v < 0.1f) -> 0.05f
                            (v > 0.9f) -> 0.95f
                            else -> v
                        }
                    }
                )
                .background(
                    color = RedError,
                    shape = RoundedCornerShape(8.dp)
                )
                .height(8.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerticalSliderTrack(
    modifier: Modifier,
    sliderState: SliderState,
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .drawBehind {
                val xPosition = size.width * sliderState.value
                val lineWidth = 4.dp.toPx()

                drawRect(
                    color = Color.White.copy(alpha = if (sliderState.isDragging) 0.9f else 0.7f),
                    topLeft = Offset(
                        x = xPosition - (lineWidth / 2),
                        y = 0f
                    ),
                    size = size.copy(width = lineWidth)
                )
            }

    )

}

@Preview
@Composable
private fun CompareSlider_Preview() {
    ComposePracticeTheme {

        CompareSlider(
            modifier = Modifier.size(300.dp),
            initialContent = { modifier ->
                Box(modifier.background(RedError))
                Image(
                    Icons.Default.Image,
                    contentDescription = "Default image",
                    modifier = modifier
                )
            },
            compareContent = { modifier ->
                Box(modifier.background(Orange))
                Image(
                    Icons.Outlined.Image,
                    contentDescription = "Default image",
                    modifier = modifier,
                )

            }
        )

    }
}

@Preview
@Composable
private fun IntegratedCompareSlider_Preview() {
    ComposePracticeTheme {

        IntegratedCompareSlider(
            modifier = Modifier.size(300.dp),
            initialContent = { modifier ->
                Box(modifier.background(RedError))
                Image(
                    Icons.Default.Image,
                    contentDescription = "Default image",
                    modifier = modifier
                )
            },
            compareContent = { modifier ->
                Box(modifier.background(Orange))
                Image(
                    Icons.Outlined.Image,
                    contentDescription = "Default image",
                    modifier = modifier,
                )

            }
        )

    }
}