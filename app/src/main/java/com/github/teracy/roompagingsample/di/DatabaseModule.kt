package com.github.teracy.roompagingsample.di

import android.app.Application
import android.arch.persistence.room.Room
import com.github.teracy.roompagingsample.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    companion object {
        val instance = DatabaseModule()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room
                .databaseBuilder(app, AppDatabase::class.java, "app_database")
                .build()
    }
}