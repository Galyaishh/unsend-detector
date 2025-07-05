package com.example.unsenddetector

import android.app.Application
import com.example.unsenddetector.data.DeletedMessageDatabase

class MyApp : Application() {
    companion object {
        lateinit var instance: MyApp
            private set

        lateinit var deletedMessageDb: DeletedMessageDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        // Initialize DeletedMessageDatabase
        deletedMessageDb = DeletedMessageDatabase.getDatabase(this)
    }
}
