package com.fd.campusid.ui.universities

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class UniversitiesViewModel : ViewModel() {

    var isSearch by mutableStateOf(false)

    fun toggleSearch() {
        isSearch = !isSearch
    }

    fun search(query: String) {
        // Perform search logic here

    }
}