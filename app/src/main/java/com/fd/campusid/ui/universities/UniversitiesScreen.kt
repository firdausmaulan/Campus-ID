package com.fd.campusid.ui.universities

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fd.campusid.helper.UiHelper
import com.fd.campusid.ui.common.DebounceSearchBar
import com.fd.campusid.ui.theme.CampusIDTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UniversitiesScreen(
    viewModel: UniversitiesViewModel
) {
    Scaffold(
        topBar = {
            if (viewModel.isSearch) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    DebounceSearchBar(
                        onSearch = { query ->
                            viewModel.search(query)
                        },
                        onClosed = {
                            viewModel.toggleSearch()
                        }
                    )
                }
            } else {
                TopAppBar(
                    title = {
                        Text(text = "CampusID")
                    },
                    colors = UiHelper.getAppBarColors(),
                    actions = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .clickable {
                                    viewModel.toggleSearch()
                                }
                        )
                    }
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(100) {
                UniversitiesItem(
                    name = "University $it",
                    url = "https://www.university$it.edu/",
                    onClick = {
                        // Handle item click
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UniversitiesScreenPreview() {
    CampusIDTheme {
        UniversitiesScreen(viewModel = UniversitiesViewModel())
    }
}