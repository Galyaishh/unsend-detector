package com.example.unsenddetector.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.unsenddetector.R
import com.example.unsenddetector.data.DeletedMessageDatabase
import com.example.unsenddetector.utilis.UsageStatsUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // בדיקה: האם יש הרשאה ל־Usage Stats?
        if (!UsageStatsUtils.hasUsageStatsPermission(this)) {
            UsageStatsUtils.requestUsageStatsPermission(this)
        }

        // בדיקה: האם יש הרשאה ל־Notification Listener
        if (!isNotificationListenerEnabled()) {
            startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"))
        }

        // קריאת הודעות שנמחקו לבדיקה
        CoroutineScope(Dispatchers.IO).launch {
            val deletedMessages = DeletedMessageDatabase.getDatabase(this@MainActivity)
                .deletedMessageDao()
                .getAll()

            deletedMessages.forEach {
                Log.d("ROOM", "${it.title}: ${it.text}")
            }
        }
    }

    private fun isNotificationListenerEnabled(): Boolean {
        val enabledListeners = android.provider.Settings.Secure.getString(
            contentResolver,
            "enabled_notification_listeners"
        )
        return enabledListeners?.contains(packageName) == true
    }
}
