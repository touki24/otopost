package com.touki.otopost

import android.app.Application
import com.touki.otopost.framework.frameworkCorePostModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@App)
            modules(listOf(
                frameworkCorePostModules,
            ))
        }
    }
}