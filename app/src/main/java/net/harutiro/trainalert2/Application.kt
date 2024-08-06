package net.harutiro.trainalert2

import android.app.Application
import androidx.room.Room
import net.harutiro.trainalert2.core.utils.room.AppDatabase

class Application: Application() {

    // デバイス構成が変更されたとき
    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        // AppDatabaseをビルドする
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
}