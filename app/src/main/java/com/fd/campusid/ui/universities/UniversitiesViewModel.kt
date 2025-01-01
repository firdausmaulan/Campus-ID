package com.fd.campusid.ui.universities

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fd.campusid.data.repository.core.RepositoryResult
import com.fd.campusid.data.repository.university.UniversityRepository
import com.fd.campusid.helper.Constant
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UniversitiesViewModel(private val universityRepository: UniversityRepository) : ViewModel() {

    var state by mutableStateOf<UniversitiesState>(UniversitiesState.Loading)

    var isRefreshing by mutableStateOf(false)
    private var currentPage = 0
    private var canLoadMore = true
    private var query = ""

    var isSearch by mutableStateOf(false)

    fun toggleSearch() {
        isSearch = !isSearch
    }

    fun search(query: String) {
        this.query = query
        currentPage = 1
        canLoadMore = true
        loadUniversities()
    }

    fun reload() {
        isRefreshing = true
        search("")
        // delay 1 second to simulate refresh
        viewModelScope.launch {
            delay(1000)
            isRefreshing = false
        }
    }

    fun loadUniversities(isLoadMore: Boolean = false) {
        if (isLoadMore && !canLoadMore) return
        viewModelScope.launch {
            state = if (isLoadMore) UniversitiesState.Success(
                (state as? UniversitiesState.Success)?.universities.orEmpty(),
                true
            )
            else UniversitiesState.Loading
            val result = universityRepository.search(query, currentPage)
            if (result is RepositoryResult.Success) {
                val items = result.data
                if (isLoadMore) {
                    if (items.isEmpty()) {
                        canLoadMore = false
                    } else {
                        val currentItems = (state as? UniversitiesState.Success)?.universities.orEmpty()
                        val universities = currentItems + items
                        state = UniversitiesState.Success(universities, true)
                        currentPage++
                        canLoadMore = items.size >= Constant.DEFAULT_LIMIT
                    }
                } else {
                    if (items.isEmpty()) {
                        state = UniversitiesState.Empty
                    } else {
                        currentPage++
                        state = UniversitiesState.Success(items, false)
                    }
                }
            } else if (result is RepositoryResult.Error) {
                state = UniversitiesState.Error(result.message)
            }
        }
    }
}