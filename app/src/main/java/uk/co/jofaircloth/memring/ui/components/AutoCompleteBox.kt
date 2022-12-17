package uk.co.jofaircloth.memring.ui.components

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import uk.co.jofaircloth.memring.data.entities.MethodPropertyEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoCompleteBox(
    onSearchTextChange: (String) -> Unit,
    methods: List<MethodPropertyEntity>,
    onMethodSelect: (MethodPropertyEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    val TAG = "AutoCompleteBox"

    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        TextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            value = selectedOptionText,
            onValueChange = {
                selectedOptionText = it
                onSearchTextChange(it)
            },
            label = { Text("Search Method") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )

        Log.d(TAG, "OPTS: $methods")

        if (methods != null && methods.isNotEmpty()) {
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                methods.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(text = selectionOption.title ?: "") },
                        onClick = {
                            expanded = false
                            onMethodSelect(selectionOption)
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
    }
}

//    val style = if (selectedOptionText == selectionOption.name) {
//        MaterialTheme.typography.bodyMedium.copy(
//            fontWeight = FontWeight.Bold,
//            color = MaterialTheme.colorScheme.onTertiary
//        )
//    } else {
//        MaterialTheme.typography.bodyMedium.copy(
//            fontWeight = FontWeight.Bold,
//            color = MaterialTheme.colorScheme.onPrimary
//        )
//    }


//    @Composable
//    @Preview(showBackground = true)
//    fun AutoCompleteBoxPreview() {
//        MemringTheme {
//            AutoCompleteBox()
//        }
//    }
