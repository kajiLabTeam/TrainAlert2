package net.harutiro.trainalert2

import android.app.Application
import androidx.room.Room
import net.harutiro.trainalert2.features.room.AppDatabase

class TrainAlertApplication : Application() {

    companion object {
        // アプリケーションインスタンスを保持
        lateinit var instance: TrainAlertApplication
            private set

        lateinit var database: AppDatabase
            private set // プロパティの可視性を設定
    }

    override fun onCreate() {
        super.onCreate()
        // アプリケーションインスタンスを保存
        instance = this

        // AppDatabaseをビルドする
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
}
