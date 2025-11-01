package fr.paita.composepractice.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedOpenableBox(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
    openableContent: @Composable() (ColumnScope.() -> Unit?)? = null,
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .clickable { expanded = !expanded },
        /*colors = CardDefaults.cardColors().copy(
            containerColor = LocalAppColors.current.cardColor
        ),*/
        //border = BorderStroke(2.dp, Color.Gray)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)
        ) {

            content.invoke(this)

            AnimatedVisibility(
                visible = expanded && openableContent != null,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Card(
                    modifier = Modifier.padding(top = 10.dp),
                    /*colors = CardDefaults.cardColors().copy(
                        containerColor = Color.Transparent
                    ),
                    border = BorderStroke(2.dp, Color.DarkGray)*/
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)
                    ) {
                        openableContent?.invoke(this)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun AnimatedOpenableBox_Preview() {
    AnimatedOpenableBox(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 20.dp),
        content = {
            Text("Hello")//, color = Color.White)
            Text("World")//, color = Color.White)
        },
        openableContent = {
            Text("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin vitae congue enim. Suspendisse tincidunt rhoncus blandit. Sed vel tristique ante, eu posuere ex. Nullam nec tellus quam. Proin mauris nisi, porta sit amet varius a, commodo ut ligula. Praesent nec erat tempor, tincidunt ante nec, pretium nisi. Morbi viverra, metus vitae interdum molestie, sapien velit egestas quam, non tempus mi ex malesuada mauris. Integer ex augue, placerat vitae dictum id, egestas ut neque.")
        },
    )
}