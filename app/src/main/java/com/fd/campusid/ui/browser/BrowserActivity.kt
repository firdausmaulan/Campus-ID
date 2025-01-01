package com.fd.campusid.ui.browser

import android.os.Bundle
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.fd.campusid.helper.Constant
import com.fd.campusid.ui.theme.CampusIDTheme

class BrowserActivity : ComponentActivity() {

    private var webView: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val title = intent.getStringExtra(Constant.KEY_TITLE)
        val url = intent.getStringExtra(Constant.KEY_URL)

        enableEdgeToEdge()

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView?.canGoBack() == true) {
                    webView?.goBack()
                } else {
                    finish()
                }
            }
        })

        setContent {
            CampusIDTheme {
                BrowserScreen(
                    title = title,
                    url = url,
                    onClosed = { finish() },
                    onWebViewCreated = { webViewInstance ->
                        webView = webViewInstance
                    }
                )
            }
        }
    }
}
