package com.touki.otopost

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.touki.otopost.framework.frameworkCorePostModules
import com.touki.otopost.framework.frameworkDatabaseModules
import com.touki.otopost.framework.frameworkHttpModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        setupKoin()

        //TODO: fungsi ini belum selesai, perlu disesuaikan dengan design pattern sekarang
        val prefs = this.getSharedPreferences("otopost", Context.MODE_PRIVATE)
        val darkMode = prefs.getInt("dark_mode", AppCompatDelegate.MODE_NIGHT_NO)
        AppCompatDelegate.setDefaultNightMode(darkMode)
    }

    private fun setupKoin() {
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@App)
            modules(listOf(
                appPostModules,
                frameworkCorePostModules,
                frameworkDatabaseModules,
                frameworkHttpModules
            ))
        }
    }
}