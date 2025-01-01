package com.fd.campusid

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CampusApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}