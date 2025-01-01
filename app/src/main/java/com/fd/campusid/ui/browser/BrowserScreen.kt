package com.fd.campusid.ui.browser

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.fd.campusid.R
import com.fd.campusid.helper.UiHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowserScreen(
    title: String?,
    url: String?,
    onClosed: () -> Unit,
    onWebViewCreated: (WebView) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = title ?: "")
                },
                colors = UiHelper.getAppBarColors(),
                actions = {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.label_close),
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable {
                                onClosed()
                            }
                    )
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        AndroidView(
            modifier = Modifier.padding(innerPadding),
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(
                            view: WebView?,
                            request: WebResourceRequest?
                        ): Boolean {
                            return false
                        }
                    }
                }.also { webView ->
                    // Pass the WebView instance back
                    onWebViewCreated(webView)
                }
            },
            update = { webView ->
                webView.loadUrl(url ?: "")
            }
        )
    }
}