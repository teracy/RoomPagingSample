package com.github.teracy.roompagingsample.di

import android.app.Application
import com.github.teracy.roompagingsample.App
import com.github.teracy.roompagingsample.di.activitymodule.MainActivityBuilder
import com.github.teracy.roompagingsample.di.activitymodule.SpeechActivityBuilder
import com.github.teracy.roompagingsample.di.activitymodule.SpeechActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    MainActivityBuilder::class,
    SpeechActivityBuilder::class,
    ViewModelModule::class,
    DatabaseModule::class
])
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun databaseModule(databaseModule: DatabaseModule): Builder
        fun build(): AppComponent
    }

    override fun inject(application: App)
}