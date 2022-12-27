@file:OptIn(ExperimentalMaterial3Api::class)

package uk.co.jofaircloth.memring.ui.preferences

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import uk.co.jofaircloth.memring.data.models.BellNames
import uk.co.jofaircloth.memring.ui.theme.MemringTheme

//@OptIn(ExperimentalLifecycleComposeApi::class)
//@Composable
//fun PreferencesScreen(
//    modifier: Modifier = Modifier
//) {
//    val viewModel: PreferencesViewModel = viewModel()
//    val viewState by viewModel.state.collectAsStateWithLifecycle()
//
//}

@Composable
fun SettingsItemList(
    columnState: LazyListState,
    innerPadding: PaddingValues,
    preferencesViewModel: PreferencesViewModel
) {
//    val preferences = preferencesViewModel.preferences

    var showBellNumbers by remember { mutableStateOf(true) }
    var hideLineNumbers by remember { mutableStateOf(true) }
    var showNotation by remember { mutableStateOf(true) }
    var showStartBells by remember { mutableStateOf(true) }
    var showAsOneLead by remember { mutableStateOf(false) }
    var showVerticalBellMarkers by remember { mutableStateOf(false) }
    var showTrebleLines by remember { mutableStateOf(true) }

    var forBellSelected by remember { mutableStateOf(4) }

    var widthRatio by remember { mutableStateOf(4f) }
    var heightRatio by remember { mutableStateOf(5f) }
    var fontSize by remember { mutableStateOf(16) }

//    treble: BlueLineStyle = BlueLineStyle(color = Color.Red, strokeWidth = 2F),
//    workingBells: BlueLineStyle = BlueLineStyle(colors = listOf(Color.Blue), strokeWidth = 4F),
//    asMultiColumn: Boolean = true,

    LazyColumn(
        state = columnState,
        contentPadding = innerPadding,
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            switch(
                onSwitchedChange = { showBellNumbers = it },
                isSwitched = showBellNumbers,
                headlineText = "Show bell numbers",
                supportingText = null
            )
        }
        item {
            switch(
                onSwitchedChange = { hideLineNumbers = it },
                isSwitched = hideLineNumbers,
                headlineText = "Hide selected bell numbers",
                supportingText = "For selected lined bells, hide their digit"
            )
        }
        item {
            switch(
                onSwitchedChange = { showStartBells = it },
                isSwitched = showStartBells,
                headlineText = "Show start bell positions",
                supportingText = null
            )
        }
        item {
            switch(
                onSwitchedChange = { showNotation = it },
                isSwitched = showNotation,
                headlineText = "Show place notation",
                supportingText = null
            )
        }
        item {
            switch(
                onSwitchedChange = { showAsOneLead = it },
                isSwitched = showAsOneLead,
                headlineText = "Show all bells as a single lead",
                supportingText = null
            )
        }
        item {
            switch(
                onSwitchedChange = { showVerticalBellMarkers = it },
                isSwitched = showVerticalBellMarkers,
                headlineText = "Show vertical bell marker lines",
                supportingText = null
            )
        }
        item {
            switch(
                onSwitchedChange = { showTrebleLines = it },
                isSwitched = showTrebleLines,
                headlineText = "Show treble line(s)",
                supportingText = null
            )
        }
        item {
            bellPicker(
                onClick = { forBellSelected = it },
                selectedBell = forBellSelected,
                headlineText = "Blue line bell"
            )
        }
        item {
            slider(
                onValueChange = { fontSize = it.toInt() },
                sliderPosition = fontSize.toFloat(),
                headlineText = "Font size",
                textMin = "10",
                textMax = "24",
                valueRange = 10f..24f,
                supportingText = "Selected: $fontSize",
                steps = 13
            )
        }
        item {
            slider(
                onValueChange = { widthRatio = it },
                sliderPosition = widthRatio,
                headlineText = "Width of blue line",
                textMin = "Narrow",
                textMax = "Wide",
                valueRange = 1f..5f,
                supportingText = null,
                steps = 3
            )
        }
        item {
            slider(
                onValueChange = { heightRatio = it },
                sliderPosition = heightRatio,
                headlineText = "Height of blue line",
                textMin = "Short",
                textMax = "Tall",
                valueRange = 1f..5f,
                supportingText = "Bell numbers will not be shown for heights lower than tall",
                steps = 3
            )
        }
    }
}

@Composable
fun slider(
    onValueChange: (Float) -> Unit,
    sliderPosition: Float,
    headlineText: String,
    textMin: String,
    textMax: String,
    valueRange: ClosedFloatingPointRange<Float>,
    supportingText: String?,
    steps: Int = 1
) {
    ListItem(
        headlineText = { Text(text = headlineText) },
        supportingText = {
            if (supportingText != null) Text(text = supportingText)
            Slider(
                value = sliderPosition,
                onValueChange = onValueChange,
                valueRange = valueRange,
                steps = steps
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                Text(
                    text = textMin,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = textMax,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    )
}


@Composable
fun switch(
    onSwitchedChange: (Boolean) -> Unit,
    isSwitched: Boolean,
    headlineText: String,
    supportingText: String?,
) {
    val icon: (@Composable () -> Unit)? = if (isSwitched) {
        {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = null,
                modifier = Modifier.size(SwitchDefaults.IconSize),
            )
        }
    } else {
        null
    }

    ListItem(
        headlineText = { Text(text = headlineText) },
        supportingText = {
            if (supportingText != null) {
                Text(
                    text = supportingText,
                )
            } else {
                null
            }
        },
        trailingContent = {
            Switch(
                modifier = Modifier.semantics { contentDescription = "Demo with icon" },
                checked = isSwitched,
                onCheckedChange = onSwitchedChange,
                thumbContent = icon
            )
        },
        modifier = Modifier.toggleable(
            role = Role.Switch,
            value = isSwitched,
            onValueChange = onSwitchedChange
        )
    )
}

@Composable
fun bellPicker(
    onClick: (Int) -> Unit,
    selectedBell: Int,
    headlineText: String,
) {
    var expanded by remember { mutableStateOf(false) }

    ListItem(
        headlineText = { Text(text = headlineText) },
        supportingText = { Text(text = "Current: ${BellNames.elementAt(selectedBell - 1)} (${selectedBell})") },
        trailingContent = {
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Choose blue line bell")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .height(230.dp)
            ) {
                BellNames.forEach { bell ->
                    var number = BellNames.indexOf(bell) + 1

                    DropdownMenuItem(
                        text = { Text(
                            buildAnnotatedString {
                                withStyle(style = SpanStyle(
                                    fontWeight = FontWeight.Bold
                                )) {
                                    append("$bell ")
                                }
                                withStyle(style = SpanStyle(
                                    color = Color.Gray
                                )) {
                                    append(" ($number)")
                                }
                            }) },
                        onClick = {
                            expanded = false
                            onClick(number)
                        }
                    )
                }
            }
        },
        modifier = Modifier.clickable {
            expanded = true
        })
}

@Preview(showBackground = true)
@Composable
fun sliderPreview() {
    var sliderPosition by remember { mutableStateOf(1f) }

    MemringTheme {
        slider(
            { sliderPosition = it},
            2f,
            "My Preview Slider",
            "Narrow",
            "Wide",
            1f..5f,
            "This is from the preview - slide me",
            1
        )
    }
}

@Preview(showBackground = true)
@Composable
fun switchPreview() {
    var switched by remember { mutableStateOf(true) }

    MemringTheme {
        switch(
            { switched = it},
            switched,
            "My Preview Switch",
            "This is from the preview - click it"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun bellPickerPreview() {
    var selected by remember { mutableStateOf(4) }

    MemringTheme {
        Column() {
            bellPicker(
                headlineText = "My Bell Picker",
                onClick = { selected = it },
                selectedBell = selected
            )
            Text(text = selected.toString())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun listPreview() {
    MemringTheme {
//        SettingsItemList()
    }
}
