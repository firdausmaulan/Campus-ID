package com.fd.campusid.ui.universities

import com.fd.campusid.data.repository.core.RepositoryResult
import com.fd.campusid.data.repository.university.UniversityRepository
import com.fd.campusid.data.repository.university.model.University
import com.fd.campusid.helper.StatusCode
import junit.framework.TestCase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
class UniversitiesViewModelTest {
    private lateinit var viewModel: UniversitiesViewModel
    private lateinit var repository: UniversityRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mock()
        viewModel = UniversitiesViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is loading`() {
        assertEquals(UniversitiesState.Loading, viewModel.state)
    }

    @Test
    fun `showSearch sets isSearch to true`() {
        assertFalse(viewModel.isSearch)
        viewModel.showSearch()
        assertTrue(viewModel.isSearch)
    }

    @Test
    fun `hideSearch sets isSearch to false`() {
        viewModel.showSearch()
        assertTrue(viewModel.isSearch)
        viewModel.hideSearch()
        assertFalse(viewModel.isSearch)
    }

    @Test
    fun `search resets pagination and loads universities`() = runTest {
        val universities = listOf(
            University(name = "Test University")
        )
        whenever(repository.search("test", 0)).thenReturn(
            RepositoryResult.Success(universities)
        )

        viewModel.search("test")
        testDispatcher.scheduler.advanceUntilIdle()

        verify(repository).search("test", 0)
        assertEquals(
            UniversitiesState.Success(universities, false),
            viewModel.state
        )
    }

    @Test
    fun `empty search result shows empty state`() = runTest {
        whenever(repository.search("", 0)).thenReturn(
            RepositoryResult.Success(emptyList())
        )

        viewModel.search("")
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(UniversitiesState.Empty, viewModel.state)
    }

    @Test
    fun `error response shows error state`() = runTest {
        val errorMessage = "Server error"
        whenever(repository.search("", 0)).thenReturn(
            RepositoryResult.Error(StatusCode.SERVER_ERROR, errorMessage)
        )

        viewModel.search("")
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(UniversitiesState.Error(errorMessage), viewModel.state)
    }

    @Test
    fun `load more appends new items to existing list`() = runTest {
        val initialUniversities = listOf(
            University(name = "First University")
        )
        val moreUniversities = listOf(
            University(name = "Second University")
        )

        whenever(repository.search("", 0)).thenReturn(
            RepositoryResult.Success(initialUniversities)
        )
        whenever(repository.search("", 1)).thenReturn(
            RepositoryResult.Success(moreUniversities)
        )

        // Initial load
        viewModel.loadUniversities()
        testDispatcher.scheduler.advanceUntilIdle()

        // Load more
        viewModel.loadUniversities(isLoadMore = true)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(
            UniversitiesState.Success(
                initialUniversities + moreUniversities,
                true
            ),
            viewModel.state
        )
    }

    @Test
    fun `load more with empty result disables further loading`() = runTest {
        val initialUniversities = listOf(
            University(name = "First University")
        )

        whenever(repository.search("", 0)).thenReturn(
            RepositoryResult.Success(initialUniversities)
        )
        whenever(repository.search("", 1)).thenReturn(
            RepositoryResult.Success(emptyList())
        )

        // Initial load
        viewModel.loadUniversities()
        testDispatcher.scheduler.advanceUntilIdle()

        // Load more
        viewModel.loadUniversities(isLoadMore = true)
        testDispatcher.scheduler.advanceUntilIdle()

        // Try to load more again
        viewModel.loadUniversities(isLoadMore = true)
        testDispatcher.scheduler.advanceUntilIdle()

        // Verify repository was only called twice (not three times)
        verify(repository, times(2)).search(any(), any())
    }

    @Test
    fun `reload resets search and shows refresh indicator`() = runTest {
        val universities = listOf(
            University(name = "Test University")
        )
        whenever(repository.search("", 0)).thenReturn(
            RepositoryResult.Success(universities)
        )

        viewModel.reload()
        assertTrue(viewModel.isRefreshing)

        // Run any pending coroutines
        testDispatcher.scheduler.advanceUntilIdle()

        verify(repository).refreshDelay()
        verify(repository).search("", 0)
        assertFalse(viewModel.isRefreshing)
    }
}