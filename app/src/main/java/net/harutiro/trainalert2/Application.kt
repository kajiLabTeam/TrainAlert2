package net.harutiro.trainalert2

import android.app.Application
import androidx.room.Room
import net.harutiro.trainalert2.features.room.AppDatabase

class TrainAlertApplication : Application() { // クラス名を変更しました

    companion object {
        lateinit var database: AppDatabase
            private set // プロパティの可視性を設定
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
