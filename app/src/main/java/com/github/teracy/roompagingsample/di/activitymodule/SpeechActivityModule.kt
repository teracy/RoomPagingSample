package com.github.teracy.roompagingsample.di.activitymodule

import android.arch.lifecycle.ViewModel
import android.support.v7.app.AppCompatActivity
import com.github.teracy.roompagingsample.di.ViewModelMapKey
import com.github.teracy.roompagingsample.ui.speech.SpeechActivity
import com.github.teracy.roompagingsample.ui.speech.SpeechViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface SpeechActivityModule {
    @Binds
    fun provideAppCompatActivity(speechActivity: SpeechActivity): AppCompatActivity

    @Binds
    @IntoMap
    @ViewModelMapKey(SpeechViewModel::class)
    fun bindSpeechViewModel(speechViewModel: SpeechViewModel): ViewModel
}