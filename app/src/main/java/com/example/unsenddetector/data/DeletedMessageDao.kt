package com.example.unsenddetector.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DeletedMessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(message: DeletedMessageEntity)

    @Query("SELECT * FROM deleted_messages ORDER BY timestamp DESC")
    suspend fun getAll(): List<DeletedMessageEntity>
}