package com.github.teracy.roompagingsample.data.paging

import android.arch.paging.DataSource
import com.github.teracy.roompagingsample.data.SchedulerProvider
import com.github.teracy.roompagingsample.data.repository.SpeechRepository
import io.reactivex.disposables.CompositeDisposable

class SpeechPageKeyedDataSourceFactory(
        repository: SpeechRepository,
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        name: String) : DataSource.Factory<Int, Speech>() {
    val source = SpeechPageKeyedDataSource(repository, schedulerProvider, compositeDisposable, name)

    override fun create(): DataSource<Int, Speech> {
        return source
    }
}