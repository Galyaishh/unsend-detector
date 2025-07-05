//package com.example.unsenddetector.data
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//
//@Database(entities = [InstagramNotification::class], version = 1,exportSchema = false)
//abstract class AppDatabase : RoomDatabase() {
//
//    abstract fun instagramNotificationDao(): InstagramNotificationDao
//
//    companion object {
//        @Volatile private var INSTANCE: AppDatabase? = null
//
//        fun getInstance(context: Context): AppDatabase =
//            INSTANCE ?: synchronized(this) {
//                INSTANCE ?: Room.databaseBuilder(
//                    context.applicationContext,
//                    AppDatabase::class.java,
//                    "unsend_detector_db"
//                ).build().also { INSTANCE = it }
//            }
//    }
//}
