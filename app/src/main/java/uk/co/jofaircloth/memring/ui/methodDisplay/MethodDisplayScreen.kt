package uk.co.jofaircloth.memring.ui.methodDisplay

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.flow.count
import uk.co.jofaircloth.memring.ui.components.SearchBar
import uk.co.jofaircloth.memring.ui.theme.MemringTheme

@Composable
fun MethodDisplayScreen(
    methodDisplayViewModel: MethodDisplayViewModel,
    modifier: Modifier = Modifier
) {

    var methodSelect by rememberSaveable { mutableStateOf("") }

    Column {
        SearchBar(
            text = methodSelect,
            onValueChange = { methodSelect = it },
            modifier.fillMaxWidth()
        )
//        Text(
//            text = methodDisplayViewModel.methodsByStage(6).count().toString(),
//        )
    }
}

@Preview(showBackground = true)
@Composable
fun MethodDisplayScreenPreview() {
    MemringTheme {
//        MethodDisplayScreen()
    }
}