package com.github.teracy.roompagingsample.di

import android.arch.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
annotation class ViewModelMapKey(val value: KClass<out ViewModel>)