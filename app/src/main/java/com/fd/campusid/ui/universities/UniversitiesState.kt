package com.fd.campusid.ui.universities

import com.fd.campusid.data.repository.university.model.University

sealed class UniversitiesState {
    object Loading : UniversitiesState()
    object Empty : UniversitiesState()
    data class Error(val message: String) : UniversitiesState()
    data class Success(val universities: List<University>, val isLoadMore: Boolean) : UniversitiesState()
}