//package com.example.unsenddetector.data
//
//import androidx.room.*
//
//@Dao
//interface InstagramNotificationDao {
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insert(notification: InstagramNotification)
//
//    @Query("UPDATE instagram_notifications SET deleted = 1 WHERE id = :id")
//    suspend fun markAsDeleted(id: String)
//
//    @Query("SELECT * FROM instagram_notifications ORDER BY timestamp DESC")
//    suspend fun getAll(): List<InstagramNotification>
//
//    @Query("DELETE FROM instagram_notifications")
//    suspend fun clearAll()
//
//    @Query("SELECT * FROM instagram_notifications WHERE id = :id")
//    suspend fun getById(id: String): InstagramNotification?
//
//    @Query("SELECT * FROM instagram_notifications WHERE threadId = :threadId ORDER BY timestamp DESC LIMIT 1")
//    suspend fun getLastMessageByThread(threadId: String): InstagramNotification?
//
//    @Query("SELECT * FROM instagram_notifications WHERE threadId = :threadId ORDER BY timestamp DESC LIMIT 1 OFFSET 1")
//    suspend fun getSecondToLastMessage(threadId: String): InstagramNotification?
//
//    @Query("SELECT * FROM instagram_notifications WHERE message = :message AND threadId = :threadId AND deleted = 0 LIMIT 1")
//    suspend fun findByMessageAndThread(message: String, threadId: String): InstagramNotification?
//
//    @Query("SELECT * FROM instagram_notifications WHERE deleted = 1")
//    suspend fun getDeletedMessages(): List<InstagramNotification>
//}
