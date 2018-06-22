package com.github.teracy.roompagingsample.ui.speech

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.github.teracy.roompagingsample.data.SchedulerProvider
import com.github.teracy.roompagingsample.data.paging.NDL_PAGE_SIZE
import com.github.teracy.roompagingsample.data.paging.NetworkState
import com.github.teracy.roompagingsample.data.paging.Speech
import com.github.teracy.roompagingsample.data.paging.SpeechPageKeyedDataSourceFactory
import com.github.teracy.roompagingsample.data.repository.SpeechRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SpeechViewModel @Inject constructor(
        application: Application,
        private val repository: SpeechRepository,
        private val schedulerProvider: SchedulerProvider,
        private val compositeDisposable: CompositeDisposable) : AndroidViewModel(application) {

    // ネットワーク状態
    var networkState: LiveData<NetworkState> = MutableLiveData()
    // 全件数
    var numberOfRecords: MutableLiveData<Int> = MutableLiveData()
    // 発言リスト
    var speeches: LiveData<PagedList<Speech>> = MutableLiveData()
    // メッセージ（エラーor件数）
    var message: MutableLiveData<String> = MutableLiveData()
    // ローディング表示
    var loading: MutableLiveData<Boolean> = MutableLiveData()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun fetchSpeech(lifecycleOwner: LifecycleOwner, name: String) {
        val factory = SpeechPageKeyedDataSourceFactory(repository, schedulerProvider, compositeDisposable, name)
        val config = PagedList.Config.Builder()
                .setInitialLoadSizeHint(NDL_PAGE_SIZE)
                .setPageSize(NDL_PAGE_SIZE)
                .build()
        loading.removeObservers(lifecycleOwner)
        speeches.removeObservers(lifecycleOwner)
        networkState.removeObservers(lifecycleOwner)
        numberOfRecords.removeObservers(lifecycleOwner)
        message.removeObservers(lifecycleOwner)

        loading = factory.source.loading
        speeches = LivePagedListBuilder(factory, config).build()
        networkState = factory.source.networkState
        numberOfRecords = factory.source.numberOfRecords
        message = factory.source.message
    }
}