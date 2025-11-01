package fr.paita.composepractice.presentation.components

import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.VectorConverter
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.paita.composepractice.ui.theme.Black05
import fr.paita.composepractice.ui.theme.DarkGrey
import fr.paita.composepractice.ui.theme.Grey
import fr.paita.composepractice.ui.theme.LightGrey
import fr.paita.composepractice.utils.AnimationTool


@Composable
fun BoxShadow(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(20.dp),
    animation: AnimationTool<Dp, AnimationVector1D> = AnimationTool.Glow(1000),
    content: @Composable (BoxScope.() -> Unit)
) {
    val elevation by animation.animateValue(
        initialValue = 0.dp,
        targetValue = 20.dp,
        typeConverter = Dp.VectorConverter,
        label = "Box_shadow"
    )
    
    Box(
        modifier = modifier
            .shadow(
                elevation,
                shape = shape,
                spotColor = LightGrey,
                ambientColor = Black05
            )
            //.background(Black05, shape = shape)
            .background(
                Brush.linearGradient(listOf(Black05, Black05, DarkGrey)),
                shape = shape
            )
            .border(2.dp, Grey, shape = shape)
            .padding(20.dp),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}

@Preview
@Composable
private fun BoxShadow_Preview() {
    Box( modifier = Modifier.size(200.dp), contentAlignment = Alignment.Center) {
        BoxShadow(
            modifier = Modifier.size(100.dp),
        ) {
            Text(
                "⭐️",
                color = LightGrey,
                fontSize = 45.sp
            )
        }
    }
}

@Preview
@Composable
private fun BoxShadow_Circle_Preview() {
    Box( modifier = Modifier.size(200.dp), contentAlignment = Alignment.Center) {
        BoxShadow(
            modifier = Modifier.size(100.dp),
            shape = CircleShape,
            animation = AnimationTool.Pulse()
        ) {
            Text(
                "⭐️",
                color = LightGrey,
                fontSize = 45.sp
            )
        }
    }
}

@Preview
@Composable
private fun BoxShadow_Cut_Preview() {
    Box( modifier = Modifier.size(200.dp), contentAlignment = Alignment.Center) {
        BoxShadow(
            modifier = Modifier.size(100.dp),
            shape = CutCornerShape(percent = 40)
        ) {
            Text(
                "⭐️",
                color = LightGrey,
                fontSize = 45.sp
            )
        }
    }
}