package fr.paita.composepractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.paita.composepractice.components.AnimatedOpenableBox
import fr.paita.composepractice.components.SuperSearchView
import fr.paita.composepractice.ui.theme.ComposePracticeTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainContent()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent() {
    val scrollState = rememberScrollState()
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp, vertical = 20.dp)

                //.background(Color.Black)
            ) {

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
                    typingDelay = 50L
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
    }
}


@Preview(showBackground = true)
@Composable
fun MainContent_Preview() {
    ComposePracticeTheme {
        MainContent()
    }
}