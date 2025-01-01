package com.fd.campusid.ui.universities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.fd.campusid.data.repository.university.model.University
import com.fd.campusid.helper.Constant
import com.fd.campusid.helper.PermissionHelper
import com.fd.campusid.ui.browser.BrowserActivity
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
        PermissionHelper.requestNotificationPermission(this)
    }

    private fun navigateToDetails(university: University) {
        val intent = Intent(this, BrowserActivity::class.java)
        intent.putExtra(Constant.KEY_TITLE, university.name)
        intent.putExtra(Constant.KEY_URL, university.webPages)
        startActivity(intent)
    }
}