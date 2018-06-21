package com.github.teracy.roompagingsample

import com.github.teracy.roompagingsample.di.DaggerAppComponent
import com.github.teracy.roompagingsample.di.DatabaseModule
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

class App : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
                .application(this)
                .databaseModule(DatabaseModule.instance)
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}