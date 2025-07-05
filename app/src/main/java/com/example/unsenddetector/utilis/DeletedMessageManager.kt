package com.example.unsenddetector.utilis

import android.app.Notification
import android.app.usage.UsageStatsManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.example.unsenddetector.data.DeletedMessageDatabase
import com.example.unsenddetector.data.DeletedMessageEntity
import com.example.unsenddetector.data.InstagramMessageSnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class DeletedMessageManager(private val context: Context) {
    private val dao = DeletedMessageDatabase.getDatabase(context).deletedMessageDao()
    private val lastMessages = mutableMapOf<String, List<InstagramMessageSnapshot>>()


    fun handleInstagramNotificationUpdate(sbn: StatusBarNotification, reason: Int? = null) {
        if (sbn.packageName != "com.instagram.android") return

        val threadKey = sbn.groupKey // אפשר גם לבנות מזהה ייחודי אחר אם צריך
        val notif = sbn.notification
        val current = extractInstagramMessages(notif)
        val previous = lastMessages[threadKey] ?: emptyList()

        val removed = previous.filterNot { old ->
            current.any { it.text == old.text && it.timestamp == old.timestamp }
        }

        removed.forEach {
            if ( (reason == null || reason == NotificationListenerService.REASON_APP_CANCEL) && !wasInstagramOpenedRecently()) {
                Log.d("DeletedMsgManager", "🧨 Deleted: ${it.from} - ${it.text}")
                saveToDatabase(threadKey, it)
            } else {
                Log.d("DeletedMsgManager", "Ignored removal. Reason=$reason")
            }
        }

        lastMessages[threadKey] = current
    }

    private fun extractInstagramMessages(notification: Notification): List<InstagramMessageSnapshot> {
        val messagesArray = notification.extras.getParcelableArray("android.messages") ?: return emptyList()
        return messagesArray.mapNotNull { parcelable ->
            try {
                val bundle = parcelable as? Bundle ?: return@mapNotNull null
                val text = bundle.getCharSequence("text")?.toString()
                val from = bundle.getCharSequence("sender")?.toString() ?: bundle.getString("name")
                val timestamp = bundle.getLong("time", 0L)
                InstagramMessageSnapshot(from, text, timestamp)
            } catch (e: Exception) {
                Log.e("NotifDebug", "⚠️ Parse fail: ${e.message}")
                null
            }
        }.also {
            Log.d("NotifDebug", "💬 Detected ${it.size} messages in android.messages")
            it.forEach { msg ->
                Log.d("NotifDebug", "🗨️ From: ${msg.from} | Text: ${msg.text} | Time: ${msg.timestamp}")
            }
        }
    }

    private fun wasInstagramOpenedRecently(): Boolean {
        val usm = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val now = System.currentTimeMillis()
        val stats = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, now - 5000, now)
        return stats.any {
            it.packageName == "com.instagram.android" && it.lastTimeUsed >= now - 5000
        }
    }

    private fun saveToDatabase(groupKey: String, msg: InstagramMessageSnapshot) {
        GlobalScope.launch(Dispatchers.IO) {
            dao.insert(
                DeletedMessageEntity(
                    packageName = "com.instagram.android",
                    title = msg.from,
                    text = msg.text,
                    timestamp = System.currentTimeMillis()
                )
            )
        }
    }

}
