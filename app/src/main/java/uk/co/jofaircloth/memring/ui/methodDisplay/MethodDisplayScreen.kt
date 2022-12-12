package uk.co.jofaircloth.memring.ui.methodDisplay

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.viewmodel.compose.viewModel
import uk.co.jofaircloth.memring.ui.components.AutoCompleteBox
import uk.co.jofaircloth.memring.ui.theme.MemringTheme
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import uk.co.jofaircloth.memring.data.models.CallSymbol
import uk.co.jofaircloth.memring.domain.BobSingleManager
import uk.co.jofaircloth.memring.ui.method.DisplayBlueLine
import uk.co.jofaircloth.memring.ui.method.DisplayBobSingle

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun MethodDisplayScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: MethodDisplayViewModel = viewModel()
    val viewState by viewModel.state.collectAsStateWithLifecycle()

    val selectedMethod = viewState.selectedMethod

    Column {
        AutoCompleteBox(
            onSearchTextChange = viewModel::onSearchTextChange,
            methods = viewState.methods,
            onMethodSelect = viewModel::onMethodSelect,
            modifier = Modifier.fillMaxWidth()
        )

        if (selectedMethod != null) {
            val bobSingle = BobSingleManager().calls(selectedMethod)
            val calls = listOf(bobSingle.bob, bobSingle.single)

            Column {
                Text(
                    text = selectedMethod.title ?: ""
                )
                Row {
                    DisplayBlueLine(
                        methodProperty = selectedMethod,
                        modifier = Modifier.background(color = Color.White)
                    )
                    Column {
                        for (call in calls) {
                            Text(text = call.toString())
                            DisplayBobSingle(
                                methodProperty = selectedMethod,
                                modifier = Modifier.background(color = Color.White),
                                call = call ?: ""
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MethodDisplayScreenPreview() {
    MemringTheme {
//        MethodDisplayScreen()
    }
}