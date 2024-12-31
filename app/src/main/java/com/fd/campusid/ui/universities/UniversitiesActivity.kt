package com.fd.campusid.ui.universities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.fd.campusid.ui.theme.CampusIDTheme

class UniversitiesActivity : ComponentActivity() {

    private val viewModel by viewModels<UniversitiesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CampusIDTheme {
                UniversitiesScreen(viewModel)
            }
        }
    }
}