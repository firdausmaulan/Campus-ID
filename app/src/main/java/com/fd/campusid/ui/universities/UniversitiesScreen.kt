package com.fd.campusid.ui.universities

import EmptyScreen
import ErrorScreen
import LoadingScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fd.campusid.data.repository.university.FakeUniversityRepositoryImpl
import com.fd.campusid.data.repository.university.model.University
import com.fd.campusid.helper.UiHelper
import com.fd.campusid.ui.common.DebounceSearchBar
import com.fd.campusid.ui.theme.CampusIDTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UniversitiesScreen(
    viewModel: UniversitiesViewModel,
    navigateToDetails: (university: University) -> Unit
) {

    val pullRefreshState = rememberPullToRefreshState()

    LaunchedEffect(Unit) {
        viewModel.loadUniversities()
    }

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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullToRefresh(
                    state = pullRefreshState,
                    isRefreshing = viewModel.isRefreshing,
                    onRefresh = { viewModel.reload() }
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (val state = viewModel.state) {
                    is UniversitiesState.Loading -> LoadingScreen()
                    is UniversitiesState.Empty -> EmptyScreen()
                    is UniversitiesState.Error -> ErrorScreen(subMessage = state.message)
                    is UniversitiesState.Success ->
                        Box(modifier = Modifier.fillMaxSize()) {
                            UniversityListView(
                                universities = state.universities,
                                onLoadMore = { viewModel.loadUniversities(true) },
                                onItemClick = { university ->
                                    navigateToDetails(university)
                                }
                            )
                            PullToRefreshBox(
                                isRefreshing = viewModel.isRefreshing,
                                onRefresh = {},
                                state = pullRefreshState,
                                modifier = Modifier.align(Alignment.TopCenter),
                                content = {}
                            )
                        }
                }
            }
        }
    }
}

@Composable
fun UniversityListView(
    universities: List<University>,
    onLoadMore: () -> Unit,
    onItemClick: (university: University) -> Unit
) {
    val listState = rememberLazyListState()

    val reachedBottom: Boolean by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem?.index != 0 && lastVisibleItem?.index == listState.layoutInfo.totalItemsCount - 1
        }
    }

    LaunchedEffect(reachedBottom) {
        if (reachedBottom) onLoadMore()
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = listState
    ) {
        items(universities.size) { index ->
            val university = universities[index]
            UniversitiesItem(
                index + 1,
                university = university,
                onClick = { onItemClick(university) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UniversitiesScreenPreview() {
    val fakeRepository = FakeUniversityRepositoryImpl()
    CampusIDTheme {
        UniversitiesScreen(
            viewModel = UniversitiesViewModel(fakeRepository),
            navigateToDetails = {})
    }
}