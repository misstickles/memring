package uk.co.jofaircloth.memring.ui.methodDisplay

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

    BoxWithConstraints() {
        Column {
            AutoCompleteBox(
                onSearchTextChange = viewModel::onSearchTextChange,
                methods = viewState.methods,
                onMethodSelect = viewModel::onMethodSelect,
                modifier = modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.secondaryContainer)
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                if (selectedMethod != null) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(this@BoxWithConstraints.maxHeight)
                            .padding(2.dp)
                            .horizontalScroll(rememberScrollState())
//                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = selectedMethod.title ?: ""
                        )
                        Row {
                            DisplayBlueLine(
                                methodProperty = selectedMethod,
                                modifier = Modifier.background(color = Color.White),
                                showLinedNumbers = false,
                                showNotation = true
                            )
                            Column {
                                // TODO can 'speed' this up by storing the generated method
                                for (call in listOf(CallSymbol.Bob, CallSymbol.Single)) {
                                    Text(text = call.name)
                                    DisplayBobSingle(
                                        methodProperty = selectedMethod,
                                        call = call,
                                        modifier = Modifier.background(color = Color.White),
                                        showNotation = true
                                    )
                                }
                            }
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