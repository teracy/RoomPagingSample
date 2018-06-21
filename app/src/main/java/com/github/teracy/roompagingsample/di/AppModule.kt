package com.github.teracy.roompagingsample.di

import android.app.Application
import android.content.Context
import com.github.teracy.roompagingsample.BuildConfig
import com.github.teracy.roompagingsample.BuildConfig.API_ROOT
import com.github.teracy.roompagingsample.data.AppSchedulerProvider
import com.github.teracy.roompagingsample.data.SchedulerProvider
import com.github.teracy.roompagingsample.data.api.NdlApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
internal object AppModule {
    @Singleton
    @Provides
    @JvmStatic
    fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    @JvmStatic
    fun provideInterceptor(): Interceptor = HttpLoggingInterceptor()
            .setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE)

    @Singleton
    @Provides
    @JvmStatic
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .addInterceptor(interceptor)
            .build()

    @Provides
    @Singleton
    @JvmStatic
    fun provideNdlApiService(client: OkHttpClient): NdlApiService = Retrofit.Builder()
            .client(client)
            .baseUrl(API_ROOT)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
            .create(NdlApiService::class.java)

    @Provides
    @Singleton
    @JvmStatic
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()

    @Provides
    @Singleton
    @JvmStatic
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    @Singleton
    @JvmStatic
    fun provideGson(): Gson = GsonBuilder().create()
}