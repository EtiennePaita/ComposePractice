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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.paita.composepractice.components.AnimatedOpenableBox
import fr.paita.composepractice.ui.theme.ComposePracticeTheme
import fr.paita.composepractice.ui.theme.DarkGrey
import fr.paita.composepractice.ui.theme.VeryLightGrey

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
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
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposePracticeTheme {
        Greeting("Android")
    }
}