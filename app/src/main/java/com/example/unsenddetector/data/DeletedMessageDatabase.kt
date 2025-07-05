package com.example.unsenddetector.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DeletedMessageEntity::class], version = 1)
abstract class DeletedMessageDatabase : RoomDatabase() {
    abstract fun deletedMessageDao(): DeletedMessageDao

    companion object {
        private var INSTANCE: DeletedMessageDatabase? = null

        fun getDatabase(context: Context): DeletedMessageDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    DeletedMessageDatabase::class.java,
                    "deleted_messages_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
