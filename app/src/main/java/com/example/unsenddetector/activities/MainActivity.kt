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
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Button
import androidx.core.view.WindowCompat
import androidx.fragment.app.commit
import com.example.unsenddetector.ui.UsersFragment

class MainActivity : AppCompatActivity() {

    private lateinit var permissionsLayout: View
    private lateinit var grantButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enableEdgeToEdge()
        permissionsLayout = findViewById(R.id.permissions_container)
        grantButton = findViewById(R.id.perm_BTN_grant)

        grantButton.setOnClickListener {
            startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
            UsageStatsUtils.requestUsageStatsPermission(this)
        }

        checkPermissionsAndNavigate()
    }

    override fun onResume() {
        super.onResume()
        checkPermissionsAndNavigate()
    }

    private fun checkPermissionsAndNavigate() {
        if (hasAllPermissions()) {
            permissionsLayout.visibility = View.GONE
            loadUsersFragment()
        } else {
            permissionsLayout.visibility = View.VISIBLE
        }
    }

    private fun hasAllPermissions(): Boolean {
        val hasNotification =
            Settings.Secure.getString(contentResolver, "enabled_notification_listeners")?.contains(packageName) == true
        val hasUsage = UsageStatsUtils.hasUsageStatsPermission(this)
        return hasNotification && hasUsage
    }

    private fun loadUsersFragment() {
        supportFragmentManager.commit {
            replace(R.id.fragment_container_view, UsersFragment())
        }
    }
}
