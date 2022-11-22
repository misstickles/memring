package uk.co.jofaircloth.memring.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.co.jofaircloth.memring.R
import uk.co.jofaircloth.memring.models.Stage
import uk.co.jofaircloth.memring.ui.theme.MemringTheme

class ComposePlay {
    @Composable
    fun DieButton(modifier: Modifier = Modifier) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                modifier = modifier,
                painter = painterResource(R.drawable.die),
                contentDescription = "Random Die"
            )
            Button(
                modifier = Modifier.padding(8.dp),
//                colors = ButtonDefaults.buttonColors(
//                    backgroundColor = MaterialTheme.colors.primary),
                onClick = {}) {
                Text("Random")
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun StageCheckboxList() {
        val options = Stage.values()
        var expanded by remember { mutableStateOf(false) }
        var selectedOptionText by remember { mutableStateOf(options[4]) }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
        ) {
            TextField(
                modifier = Modifier.menuAnchor(),
                textStyle = TextStyle(fontWeight = FontWeight.Bold),
                readOnly = true,
                value = "$selectedOptionText (${selectedOptionText.number})",
                onValueChange = {},
//                label = { Text( "Random Chooser" ) },
//                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(
                    textColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                options.forEach { selectedOption ->
                    val text = "${selectedOption.name} (${selectedOption.number})"
                    if (selectedOption == selectedOptionText) {
                        DropdownMenuItem(
                            text = { Text(text) },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Done,
                                    contentDescription = null,
                                )
                            },
                            onClick = {
                                selectedOptionText = selectedOption
                                expanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    } else {
                        DropdownMenuItem(
                            text = { Text(text = text) },
                            onClick = {
                                selectedOptionText = selectedOption
                                expanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                }
            }
        }
    }



//    @Preview(showBackground = true)
//    @Composable
//    fun PreviewDieButton() {
//        MemringTheme {
//            DieButton()
//        }
//    }

    @Preview(showBackground = true)
    @Composable
    fun PreviewStageCheckboxList() {
        MemringTheme {
            StageCheckboxList()
        }
    }
}