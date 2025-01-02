package com.fd.campusid.ui.download

import com.fd.campusid.data.repository.core.RepositoryResult
import com.fd.campusid.data.repository.university.UniversityRepository
import com.fd.campusid.data.repository.university.model.DownloadUniversity
import com.fd.campusid.helper.Constant
import com.fd.campusid.helper.StatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*
import junit.framework.TestCase.*

@OptIn(ExperimentalCoroutinesApi::class)
class DownloadViewModelTest {
    private lateinit var viewModel: DownloadViewModel
    private lateinit var repository: UniversityRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mock()
        viewModel = DownloadViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is loading`() {
        assertEquals(DownloadState.Loading, viewModel.state)
    }

    @Test
    fun `isDownloadCompleted sets completed state when download is completed`() = runTest {
        whenever(repository.isDownloadCompleted()).thenReturn(true)

        viewModel.isDownloadCompleted()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(DownloadState.Completed, viewModel.state)
        verify(repository, never()).download()
    }

    @Test
    fun `isDownloadCompleted starts download when not completed`() = runTest {
        whenever(repository.isDownloadCompleted()).thenReturn(false)
        whenever(repository.download()).thenReturn(
            RepositoryResult.Success(
                DownloadUniversity(
                    status = DownloadStatus.COMPLETED,
                    offset = 0,
                    total = 1 * Constant.DEFAULT_LIMIT
                )
            )
        )

        viewModel.isDownloadCompleted()
        testDispatcher.scheduler.advanceUntilIdle()

        verify(repository).isDownloadCompleted()
        verify(repository).download()
        assertEquals(DownloadState.Completed, viewModel.state)
    }

    @Test
    fun `downloadUniversities updates progress and continues until completed`() = runTest {
        val download1 = DownloadUniversity(
            status = DownloadStatus.IN_PROGRESS,
            offset = 0,
            total = 1 * Constant.DEFAULT_LIMIT
        )
        val download2 = DownloadUniversity(
            status = DownloadStatus.COMPLETED,
            offset = 1 * Constant.DEFAULT_LIMIT,
            total = 2 * Constant.DEFAULT_LIMIT
        )

        whenever(repository.download())
            .thenReturn(RepositoryResult.Success(download1))
            .thenReturn(RepositoryResult.Success(download2))

        viewModel.downloadUniversities()
        testDispatcher.scheduler.advanceUntilIdle()

        verify(repository, times(2)).download()
        assertEquals(DownloadState.Completed, viewModel.state)
        assertEquals("200 universities downloaded", viewModel.inProgressMessage)
    }

    @Test
    fun `downloadUniversities handles error state`() = runTest {
        val errorMessage = "Server error"
        whenever(repository.download()).thenReturn(
            RepositoryResult.Error(StatusCode.SERVER_ERROR, errorMessage)
        )

        viewModel.downloadUniversities()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(DownloadState.Error(errorMessage), viewModel.state)
    }

    @Test
    fun `downloadUniversities updates progress message`() = runTest {
        val download = DownloadUniversity(
            status = DownloadStatus.IN_PROGRESS,
            offset = 2 * Constant.DEFAULT_LIMIT,
            total = 3 * Constant.DEFAULT_LIMIT
        )

        val dummyMessage = "Stop recursive call"
        whenever(repository.download())
            .thenReturn(RepositoryResult.Success(download))
            .thenReturn(RepositoryResult.Error(StatusCode.DUMMY_ERROR, dummyMessage))

        viewModel.downloadUniversities()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(DownloadState.Error(dummyMessage), viewModel.state)
        assertEquals("300 universities downloaded", viewModel.inProgressMessage)
    }

    @Test
    fun `downloadUniversities continues when status is not completed`() = runTest {
        val download1 = DownloadUniversity(
            status = DownloadStatus.IN_PROGRESS,
            offset = 0,
            total = 1 * Constant.DEFAULT_LIMIT
        )
        val download2 = DownloadUniversity(
            status = DownloadStatus.IN_PROGRESS,
            offset = 1 * Constant.DEFAULT_LIMIT,
            total = 2 * Constant.DEFAULT_LIMIT
        )
        val download3 = DownloadUniversity(
            status = DownloadStatus.COMPLETED,
            offset = 2 * Constant.DEFAULT_LIMIT,
            total = 3 * Constant.DEFAULT_LIMIT
        )

        whenever(repository.download())
            .thenReturn(RepositoryResult.Success(download1))
            .thenReturn(RepositoryResult.Success(download2))
            .thenReturn(RepositoryResult.Success(download3))

        viewModel.downloadUniversities()
        testDispatcher.scheduler.advanceUntilIdle()

        verify(repository, times(3)).download()
        assertEquals(DownloadState.Completed, viewModel.state)
        assertEquals("300 universities downloaded", viewModel.inProgressMessage)
    }

    @Test
    fun `downloadUniversities handles null values in DownloadUniversity`() = runTest {
        val download = DownloadUniversity(
            status = DownloadStatus.IN_PROGRESS,
            offset = null,
            total = null
        )

        val dummyMessage = "Stop recursive call"
        whenever(repository.download())
            .thenReturn(RepositoryResult.Success(download))
            .thenReturn(RepositoryResult.Error(StatusCode.DUMMY_ERROR, dummyMessage))

        viewModel.downloadUniversities()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(DownloadState.Error(dummyMessage), viewModel.state)
        assertEquals("null universities downloaded", viewModel.inProgressMessage)
    }
}