package fr.paita.composepractice.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.VectorConverter
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.paita.composepractice.presentation.components.AnimatedOpenableBox
import fr.paita.composepractice.presentation.components.BoxShadow
import fr.paita.composepractice.presentation.components.SuperSearchView
import fr.paita.composepractice.presentation.screens.playground.PlaygroundScreen
import fr.paita.composepractice.ui.theme.Black
import fr.paita.composepractice.ui.theme.ComposePracticeTheme
import fr.paita.composepractice.ui.theme.LightGrey
import fr.paita.composepractice.utils.AnimationTool

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposePracticeTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text("Compose practice")
                            }
                        )
                    },
                    bottomBar = {
                        BottomAppBar(

                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                text = "Bottom app bar",
                            )
                        }
                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = { }) {
                            Icon(
                                Icons.Filled.Build,
                                contentDescription = ""
                            )
                        }
                    }
                ) { innerPadding ->
                    MainContent(innerPadding)
                    //PlaygroundScreen(Modifier.padding(innerPadding))
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    innerPadding: PaddingValues
) {
    val scrollState = rememberScrollState()
    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .background(Black)
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(innerPadding)
            .padding(horizontal = 20.dp, vertical = 20.dp)

        //.background(Color.Black)
    ) {

        /*val pulseAnimation = AnimationTool.Pulse<Dp>(3000)
        val boxSize by pulseAnimation.animateValue(
            initialValue = 100.dp,
            targetValue = 130.dp,
            typeConverter = Dp.VectorConverter,
            label = "BoxSizeAnimation"
        )*/

        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            BoxShadow(
                modifier = Modifier
                    .size(100.dp),
            ) {
                Text(
                    "⭐️",
                    color = LightGrey,
                    fontSize = 45.sp
                )
            }

            Spacer(Modifier.width(30.dp))
            BoxShadow(
                modifier = Modifier
                    .size(100.dp),
                animation = AnimationTool.Pulse<Dp>()
            ) {
                Text(
                    "⭐️",
                    color = LightGrey,
                    fontSize = 45.sp
                )
            }

        }

        Spacer(modifier = Modifier.height(50.dp))

        SuperSearchView(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            hints = listOf(
                "https://wwww.google.com/education-valider",
                "https://www.instagram.com/fr/reels/udd-subco",
                "https://www.tiktok.com/fr/videos/vzoi-scojpp"
            ),
            typingDelay = 50L,
            value = text,
            onValueChange = { it -> text = it},
        )

        Spacer(modifier = Modifier.height(20.dp))

        AnimatedOpenableBox(
            Modifier
                .fillMaxWidth(),
            content = {
                Text("Hello")//, color = Color.White)
                Text("World")//, color = Color.White)
            },
            openableContent = {
                Text("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin vitae congue enim. Suspendisse tincidunt rhoncus blandit. Sed vel tristique ante, eu posuere ex. Nullam nec tellus quam. Proin mauris nisi, porta sit amet varius a, commodo ut ligula. Praesent nec erat tempor, tincidunt ante nec, pretium nisi. Morbi viverra, metus vitae interdum molestie, sapien velit egestas quam, non tempus mi ex malesuada mauris. Integer ex augue, placerat vitae dictum id, egestas ut neque.")
            },
        )


    }

}


@Preview(showBackground = true)
@Composable
fun MainContent_Preview() {
    ComposePracticeTheme {
        MainContent(PaddingValues(10.dp))
    }
}