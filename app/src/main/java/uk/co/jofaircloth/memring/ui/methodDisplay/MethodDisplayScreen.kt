package uk.co.jofaircloth.memring.ui.methodDisplay

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import uk.co.jofaircloth.memring.ui.components.SearchBar
import uk.co.jofaircloth.memring.ui.theme.MemringTheme

@Composable
fun MethodDisplayScreen(
    modifier: Modifier = Modifier,
    methodDisplayViewModel: MethodDisplayViewModel = viewModel()
) {
    var methodSelect by rememberSaveable { mutableStateOf("") }

    SearchBar(
        text = methodSelect,
        onValueChange = { methodSelect = it},
        modifier
            .fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun MethodDisplayScreenPreview() {
    MemringTheme {
        MethodDisplayScreen()
    }
}