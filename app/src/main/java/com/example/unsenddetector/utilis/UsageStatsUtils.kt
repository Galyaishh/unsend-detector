package com.example.unsenddetector.utilis

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.text.format.DateUtils
import android.util.Log
import android.app.AppOpsManager
import android.os.Process

object UsageStatsUtils {

    /**
     * בודק האם יש הרשאה ל־Usage Stats
     */
    fun hasUsageStatsPermission(context: Context): Boolean {
        val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            Process.myUid(),
            context.packageName
        )
        return mode == AppOpsManager.MODE_ALLOWED
    }

    /**
     * פותח את מסך ההרשאות ל־Usage Stats אם אין הרשאה
     */
    fun requestUsageStatsPermission(context: Context) {
        val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    /**
     * בודק אם אינסטגרם נפתחה לאחרונה (withinMillis = זמן בשניות)
     */
    fun wasInstagramOpenedRecently(context: Context, withinMillis: Long = 4000): Boolean {
        val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val endTime = System.currentTimeMillis()
        val beginTime = endTime - withinMillis

        val stats: List<UsageStats> = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            beginTime,
            endTime
        )

        stats.forEach { usageStat ->
            if (usageStat.packageName == "com.instagram.android" &&
                usageStat.lastTimeUsed >= beginTime
            ) {
                Log.d("USAGE", "Instagram was opened recently at ${usageStat.lastTimeUsed}")
                return true
            }
        }
        return false
    }
}
