package fr.paita.composepractice.components

import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.paita.composepractice.ui.theme.Orange
import fr.paita.composepractice.ui.theme.Pink
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun SuperSearchView(
    modifier: Modifier = Modifier,
    hints: List<String>? = null,
    typingDelay: Long = 100L,
    sentenceDelay: Long = 1500L,
    writingChar: String = "",
) {
    var text by remember { mutableStateOf("") }
    var displayedHint: String? by remember { mutableStateOf(null) }

    if (hints?.isNotEmpty() == true && text.isEmpty()) {

        var hintIndex by remember { mutableIntStateOf(0) }
        val currentText = hints[hintIndex]

        // Typewriter Animation
        LaunchedEffect(key1 = currentText) {
            Log.d("[Typewriter]", "Writing : $currentText")
            for (i in currentText.indices) {
                displayedHint = currentText.substring(0, i + 1) + writingChar
                delay(typingDelay)
            }
            Log.d("[Typewriter]", "PAUSE")
            delay(sentenceDelay)

            hintIndex = (hintIndex + 1) % hints.size
        }
    }

    // Stroke UI animation
    val infiniteTransition = rememberInfiniteTransition(label = "gradient_animation")
    val gradientColors = listOf(Orange, Pink)
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation_angle"
    )
    // Calculate start and end offsets based on the animated angle to create
    // the rotational effect
    val animatedBrush = Brush.linearGradient(
        colors = gradientColors,
        start = Offset(
            x = (100f * cos(Math.toRadians(angle.toDouble()))).toFloat() + 100f,
            y = (100f * sin(Math.toRadians(angle.toDouble()))).toFloat() + 100f
        ),
        end = Offset(
            x = (100f * cos(Math.toRadians((angle + 180).toDouble()))).toFloat() + 100f,
            y = (100f * sin(Math.toRadians((angle + 180).toDouble()))).toFloat() + 100f
        )
    )

    OutlinedTextField(
        modifier = modifier.border(
            width = 4.dp,
            brush = animatedBrush,
            shape = RoundedCornerShape(50.dp)
        ),
        value = text,
        onValueChange = { it -> text = it},
        textStyle = TextStyle(
            fontSize = 18.sp,
            fontFamily = FontFamily.Monospace
        ),
        placeholder = displayedHint?.let { h ->
            {
                Text(
                    text = h,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily.Monospace,
                        color = Color.Gray,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        },
        shape = RoundedCornerShape(50.dp),
        singleLine = true,
    )
}

@Preview
@Composable
private fun SuperSearchView_Preview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        SuperSearchView(
            modifier = Modifier.align(Alignment.Center),
            hints = listOf(
                "https://wwww.google.com/education-valider",
                "https://www.instagram.com/fr/reels/udd-subco",
                "https://www.tiktok.com/fr/videos/vzoi-scojpp"
            )
        )
    }

}
