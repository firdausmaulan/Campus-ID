package com.fd.campusid.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebounceSearchBar(
    onSearch: (String) -> Unit,
    onClosed: () -> Unit = {},
    modifier: Modifier = Modifier,
    placeholder: String = "Search",
    debounceTimeMillis: Long = 1000
) {
    var query by remember { mutableStateOf("") }
    var isEmpty by remember { mutableStateOf(true) }

    // Create a coroutine scope that follows the lifecycle of the composable
    val scope = rememberCoroutineScope()

    // Create a job that can be cancelled
    var searchJob by remember { mutableStateOf<Job?>(null) }

    // Debounced search function
    val debouncedSearch = remember {
        { searchText: String ->
            searchJob?.cancel() // Cancel any ongoing job
            searchJob = scope.launch {
                delay(debounceTimeMillis) // Wait for the specified delay
                onSearch(searchText) // Perform the search
            }
        }
    }

    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                placeholder = {
                    Text(text = placeholder)
                },
                query = query,
                onQueryChange = { newQuery ->
                    query = newQuery
                    isEmpty = newQuery.isEmpty()
                    debouncedSearch(newQuery)
                },
                onSearch = { },
                expanded = false,
                onExpandedChange = { },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear",
                        modifier = Modifier.clickable {
                            if (!isEmpty) {
                                query = ""
                                isEmpty = true
                            }
                            onClosed()
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
        },
        expanded = false,
        onExpandedChange = { },
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {}
}

@Preview(showBackground = true)
@Composable
fun DebounceSearchBarPreview() {
    DebounceSearchBar(onSearch = { query ->
        // Handle the search query here
    })
}