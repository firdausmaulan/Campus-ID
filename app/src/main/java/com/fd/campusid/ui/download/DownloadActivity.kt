package com.fd.campusid.ui.download

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.fd.campusid.helper.PermissionHelper
import com.fd.campusid.ui.theme.CampusIDTheme
import com.fd.campusid.ui.universities.UniversitiesActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DownloadActivity : ComponentActivity() {

    @Inject
    lateinit var viewModel: DownloadViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CampusIDTheme {
                DownloadScreen(
                    viewModel = viewModel,
                    navigateToUniversities = { navigateToUniversities() }
                )
            }
        }
        PermissionHelper.requestNotificationPermission(this)
    }

    private fun navigateToUniversities() {
        val intent = Intent(this, UniversitiesActivity::class.java)
        startActivity(intent)
        finish()
    }
}