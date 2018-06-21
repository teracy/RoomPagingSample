package com.github.teracy.roompagingsample.di.activitymodule

import android.arch.lifecycle.ViewModel
import android.support.v7.app.AppCompatActivity
import com.github.teracy.roompagingsample.di.ViewModelMapKey
import com.github.teracy.roompagingsample.ui.MainActivity
import com.github.teracy.roompagingsample.ui.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface MainActivityModule {
    @Binds
    fun provideAppCompatActivity(mainActivity: MainActivity): AppCompatActivity

    @Binds
    @IntoMap
    @ViewModelMapKey(MainViewModel::class)
    fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel
}