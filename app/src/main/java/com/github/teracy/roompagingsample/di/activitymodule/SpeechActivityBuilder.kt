package com.github.teracy.roompagingsample.di.activitymodule

import com.github.teracy.roompagingsample.ui.speech.SpeechActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface SpeechActivityBuilder {
    @ContributesAndroidInjector(modules = [SpeechActivityModule::class])
    fun contributeSpeechActivity(): SpeechActivity
}