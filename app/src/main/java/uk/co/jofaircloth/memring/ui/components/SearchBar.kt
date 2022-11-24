package uk.co.jofaircloth.memring.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.co.jofaircloth.memring.ui.theme.MemringTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier) {

    TextField(
        value = text,
        onValueChange = onValueChange,
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null
            )
        },
        placeholder = { Text(text = "Search Method") },
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.tertiaryContainer)
    )
}

@Preview(
    showBackground = true,
    name = "Bar Dark",
    uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SearchBarPreview() {
    var text by rememberSaveable { mutableStateOf("") }

    MemringTheme() {
        SearchBar(
            text = text,
            onValueChange = { text = it},
            modifier = Modifier
        )
    }
}
