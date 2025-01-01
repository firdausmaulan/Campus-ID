package com.fd.campusid.helper

import android.app.Activity

object PermissionHelper {

    const val REQUEST_CODE_NOTIFICATION_PERMISSION = 101

    fun requestNotificationPermission(activity: Activity) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            val permissions = arrayOf(android.Manifest.permission.POST_NOTIFICATIONS)
            activity.requestPermissions(permissions, REQUEST_CODE_NOTIFICATION_PERMISSION)
        }
    }

}