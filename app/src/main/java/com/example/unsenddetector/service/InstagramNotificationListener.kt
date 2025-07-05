package com.example.unsenddetector.service

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.example.unsenddetector.utilis.DeletedMessageManager


class InstagramNotificationListener : NotificationListenerService() {

    private lateinit var deletedMessageManager: DeletedMessageManager

    override fun onCreate() {
        super.onCreate()
        deletedMessageManager = DeletedMessageManager(this)
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        deletedMessageManager.handleInstagramNotificationUpdate(sbn)
    }

    override fun onNotificationRemoved(
        sbn: StatusBarNotification?,
        rankingMap: RankingMap?,
        reason: Int
    ) {
        if (sbn != null) {
            deletedMessageManager.handleInstagramNotificationUpdate(sbn,reason)
        }
    }

}
