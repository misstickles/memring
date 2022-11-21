package uk.co.jofaircloth.memring

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import uk.co.jofaircloth.memring.domain.PlaceNotationManager
import uk.co.jofaircloth.memring.ui.components.GenerateLine
import uk.co.jofaircloth.memring.ui.theme.MemringTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MemringTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Column() {
        Row() {
            GenerateLine(
                method = PlaceNotationManager().generateRows( "5.1.5.1.5.1.5.1.5", 5) // plain hunt
//                method = PlaceNotationManager().generateRows("5.1.5.1.5,125", 5) // plain bob doubles
//                method = PlaceNotationManager().generateRows("-36-14-12-36.14-12.56,12", 6)
            )
        }

//        Column(Modifier.fillMaxWidth()) {
//            Text(text = "Hello $name!")
//
//            ComposePlay().StageCheckboxList()
//
//            ComposePlay().DieButton(
//                modifier = Modifier
//                    .size(120.dp)
//                    .padding(top = 50.dp, bottom = 30.dp)
//            )
//        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MemringTheme {
        Greeting("Android")
    }
}