package com.fd.campusid.ui.download

import LoadingScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import com.fd.campusid.data.repository.university.FakeUniversityRepositoryImpl
import com.fd.campusid.ui.common.ConfirmationBottomSheet
import com.fd.campusid.ui.theme.CampusIDTheme

@Composable
fun DownloadScreen(
    viewModel: DownloadViewModel,
    navigateToUniversities: () -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.isDownloadCompleted()
    }

    Box {
        when (viewModel.state) {
            is DownloadState.Loading -> LoadingScreen()
            is DownloadState.Error -> {
                LoadingScreen(viewModel.inProgressMessage)
                ConfirmationBottomSheet(
                    title = "Download Failed",
                    message = "Do you want to retry?",
                    onConfirmClick = { viewModel.downloadUniversities() },
                    onDismissClick = { navigateToUniversities() }
                )
            }

            is DownloadState.InProgress -> LoadingScreen(viewModel.inProgressMessage)
            is DownloadState.Completed -> navigateToUniversities()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DownloadScreenPreview() {
    val fakeRepository = FakeUniversityRepositoryImpl()
    CampusIDTheme {
        DownloadScreen(viewModel = DownloadViewModel(fakeRepository), navigateToUniversities = {})
    }
}