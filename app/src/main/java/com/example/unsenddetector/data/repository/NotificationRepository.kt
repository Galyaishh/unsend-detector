//package com.example.unsenddetector.data.repository
//
//import com.example.unsenddetector.data.InstagramNotification
//import com.example.unsenddetector.data.InstagramNotificationDao
//
//class NotificationRepository(
//    private val dao: InstagramNotificationDao
//) {
//    suspend fun saveNotification(notification: InstagramNotification) {
//        dao.insert(notification)
//    }
//
//    suspend fun markNotificationAsDeleted(key: String) {
//        dao.markAsDeleted(key)
//    }
//
//    suspend fun getAllNotifications(): List<InstagramNotification> {
//        return dao.getAll()
//    }
//
//    suspend fun clear() {
//        dao.clearAll()
//    }
//}
