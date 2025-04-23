package com.bookblitzpremium.upcomingproject.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamMemberDropdown(
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Select team member"
) {
    var expanded by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }

    val filtered = remember(query) {
        if (query.isBlank()) options else options.filter {
            it.contains(query, ignoreCase = true)
        }
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        TextField(
            value = if (expanded) query else (selectedOption ?: ""),
            onValueChange = { query = it },
            readOnly = true,
            placeholder = { Text(placeholder) },
            leadingIcon = {
                Icon(Icons.Default.Person, contentDescription = null)
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp), // Rounded like the mockup
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                query = ""
            }
        ) {
            filtered.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    leadingIcon = {
                        Icon(Icons.Default.Person, contentDescription = null)
                    },
                    trailingIcon = {
                        if (option == selectedOption) {
                            Icon(Icons.Default.Check, contentDescription = null)
                        }
                    },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                        query = ""
                    },
                    contentPadding = PaddingValues(horizontal = 32.dp, vertical = 8.dp),
                )
            }
        }
    }
}


@Composable
fun MyScreen() {
    var selected by remember { mutableStateOf<String?>(null) }
    val names = listOf(
        "4 Person -  1 Room",
        "8 Person -  2 Room",
        "12 Person - 3 Room",
        "16 Person - 4 Room",
        "20 Person - 5 Room",
        "24 Person - 6 Room",
    )

    TeamMemberDropdown(
        options = names,
        selectedOption = selected,
        onOptionSelected = { selected = it },
        modifier = Modifier.padding(16.dp)
    )
}