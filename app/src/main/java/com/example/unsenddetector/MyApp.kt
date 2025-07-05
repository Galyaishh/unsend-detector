package com.example.unsenddetector

import android.app.Application
import com.example.unsenddetector.data.DeletedMessageDatabase
import com.example.unsenddetector.data.DeletedMessageDao

class MyApp : Application() {
    companion object {
        lateinit var instance: MyApp
            private set

        lateinit var deletedMessageDao: DeletedMessageDao
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        val db = DeletedMessageDatabase.getDatabase(this)
        deletedMessageDao = db.deletedMessageDao()
    }
}
