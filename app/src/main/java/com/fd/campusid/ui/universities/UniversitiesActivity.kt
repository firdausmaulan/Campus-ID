package com.fd.campusid.ui.universities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.fd.campusid.data.repository.university.model.University
import com.fd.campusid.ui.theme.CampusIDTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UniversitiesActivity : ComponentActivity() {

    @Inject
    lateinit var viewModel: UniversitiesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CampusIDTheme {
                UniversitiesScreen(
                    viewModel = viewModel,
                    navigateToDetails = {
                        navigateToDetails(it)
                    }
                )
            }
        }
    }

    private fun navigateToDetails(university: University) {
        TODO("Not yet implemented")
    }
}