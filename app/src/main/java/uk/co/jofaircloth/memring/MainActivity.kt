package uk.co.jofaircloth.memring

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.co.jofaircloth.memring.domain.PlaceNotationManager
import uk.co.jofaircloth.memring.ui.components.ComposePlay
import uk.co.jofaircloth.memring.ui.components.GenerateLine
import uk.co.jofaircloth.memring.ui.theme.MemringTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp(modifier = Modifier.fillMaxSize())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(modifier: Modifier = Modifier) {
    MemringTheme {
        Scaffold(
            bottomBar = {}
        ) { padding ->
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.surface
            ) {
                Greeting(
                    Modifier.padding(padding),
                    "Android"
                )
            }
        }
    }
}

@Composable
fun Greeting(
    modifier: Modifier = Modifier,
    name: String
) {
//    Column() {
//        GenerateLine(
//            method = PlaceNotationManager().generateRows( "5.1.5.1.5.1.5.1.5", 5) // plain hunt
////                method = PlaceNotationManager().generateRows("5.1.5.1.5,125", 5) // plain bob doubles
////                method = PlaceNotationManager().generateRows("-36-14-12-36.14-12.56,12", 6)
//        )
//    }

        Column(Modifier.fillMaxWidth()) {
            Text(text = "Hello $name!")

            ComposePlay().StageCheckboxList()

            ComposePlay().DieButton(
                modifier = Modifier
                    .size(120.dp)
                    .padding(top = 50.dp, bottom = 30.dp)
            )
        }
}

@Composable
fun MemringBottomNavigation(modifier: Modifier = Modifier) {
}

@Preview(showBackground = true)
@Composable
fun MyAppPreview() {
    MemringTheme {
        MyApp()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MemringTheme {
        Greeting(name = "MemRing")
    }
}