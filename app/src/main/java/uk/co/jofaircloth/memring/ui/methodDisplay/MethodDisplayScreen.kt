package uk.co.jofaircloth.memring.ui.methodDisplay

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.*
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.viewmodel.compose.viewModel
import uk.co.jofaircloth.memring.ui.components.AutoCompleteBox
import uk.co.jofaircloth.memring.ui.theme.MemringTheme
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import uk.co.jofaircloth.memring.R
import uk.co.jofaircloth.memring.data.models.CallSymbol
import uk.co.jofaircloth.memring.ui.method.BlueLineUiModel
import uk.co.jofaircloth.memring.ui.method.DisplayBlueLine
import uk.co.jofaircloth.memring.ui.method.DisplayBobSingle

const val TAG = "MethodDisplayScreen"

@OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MethodDisplayScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: MethodDisplayViewModel = viewModel()
    val viewState by viewModel.state.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val fabScope = rememberCoroutineScope()

//    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            floatingActionButton(
                onFabClick = {
                    fabScope.launch {
                        snackbarHostState.showSnackbar("...pretend I'm a modal popup with all your settings needs...")
                    }
                })
        },
        content = { padding ->
            methodScreen(
                padding = padding,
                viewModel = viewModel,
                viewState = viewState
            )
        }
    )
}

@Composable
fun methodScreen(
    padding: PaddingValues,
    viewModel: MethodDisplayViewModel,
    viewState: MethodDisplayViewState,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val localDensity = LocalDensity.current

    var heightDp by remember {
        mutableStateOf(0.dp)
    }

    val selectedMethod = viewState.selectedMethod

    Column(
        modifier = Modifier
            .padding(padding)
            .background(color = Color.White)
            .fillMaxSize()
            .onGloballyPositioned { coordinates ->
                heightDp = with(localDensity) { coordinates.size.height.toDp() }
            }
    ) {
        AutoCompleteBox(
            onSearchTextChange = viewModel::onSearchTextChange,
            methods = viewState.methods,
            onMethodSelect = viewModel::onMethodSelect,
            modifier = modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.secondaryContainer)
        )
        if (selectedMethod != null) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
                    .height(heightDp)
                    .horizontalScroll(scrollState)
            ) {
                Text(
                    text = selectedMethod.title ?: "",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleLarge
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    val input = BlueLineUiModel(
                        methodProperty = selectedMethod,
                        modifier = Modifier.background(color = Color.White),
                        showLinedNumbers = false,
                        showNotation = true
                    )
                    DisplayBlueLine(input)
                    Column {
                        // TODO can 'speed' this up by storing the generated method
                        for (call in listOf(CallSymbol.Bob, CallSymbol.Single)) {
                            Text(
                                text = call.name,
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.titleSmall
                            )
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


@Composable
fun floatingActionButton(
    onFabClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val blueLineSettingsDescription = stringResource(R.string.fab_blueline_settings)

    FloatingActionButton(
        onClick = onFabClick,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        shape = MaterialTheme.shapes.small, // RoundedCornerShape(16.dp),
        modifier = modifier.semantics {
            contentDescription = blueLineSettingsDescription
        }
    ) {
        Icon(
            imageVector = Icons.Rounded.Settings,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MethodDisplayScreenPreview() {
    MemringTheme {
//        MethodDisplayScreen()
    }
}