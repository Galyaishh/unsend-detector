package com.example.unsenddetector.utilis

import android.app.ActivityManager
import android.content.Context

object ForegroundDetector {
    fun isInstagramInForeground(context: Context): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningProcesses = activityManager.runningAppProcesses
        return runningProcesses.any { process ->
            process.processName == "com.instagram.android" &&
                    process.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
        }
    }
}
