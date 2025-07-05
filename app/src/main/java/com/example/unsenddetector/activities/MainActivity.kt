package com.example.unsenddetector.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.unsenddetector.R
import com.example.unsenddetector.utilis.UsageStatsUtils
import android.provider.Settings
import android.content.Context
import android.provider.Settings.Secure
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.fragment.app.commit
import com.example.unsenddetector.ui.UsersFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(R.layout.activity_main)

        if (!hasAllPermissions()) {
            showPermissionsDialog()
        } else {
            loadUsersFragment()
        }
    }

    private fun hasAllPermissions(): Boolean {
        return isNotificationListenerEnabled() && UsageStatsUtils.hasUsageStatsPermission(this)
    }

    private fun showPermissionsDialog() {
        AlertDialog.Builder(this)
            .setTitle("נדרשות הרשאות")
            .setMessage("כדי לזהות הודעות שנמחקו, יש להפעיל הרשאות:\n• גישה להתראות\n• גישה לשימוש באפליקציות")
            .setPositiveButton("אישור") { _, _ ->
                startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
                UsageStatsUtils.requestUsageStatsPermission(this)
            }
            .setNegativeButton("בטל", null)
            .setOnDismissListener {
                if (hasAllPermissions()) {
                    loadUsersFragment()
                }
            }
            .show()
    }

    private fun loadUsersFragment() {
        supportFragmentManager.commit {
            replace(R.id.fragment_container_view, UsersFragment())
        }
    }

    private fun isNotificationListenerEnabled(): Boolean {
        val enabledListeners = Secure.getString(contentResolver, "enabled_notification_listeners")
        return enabledListeners?.contains(packageName) == true
    }
}

