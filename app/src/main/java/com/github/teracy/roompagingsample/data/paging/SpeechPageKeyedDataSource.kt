package com.github.teracy.roompagingsample.data.paging

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import com.github.teracy.roompagingsample.data.SchedulerProvider
import com.github.teracy.roompagingsample.data.api.ApiErrorTransformer
import com.github.teracy.roompagingsample.data.api.response.SpeechData
import com.github.teracy.roompagingsample.data.api.response.SpeechRecord
import com.github.teracy.roompagingsample.data.repository.SpeechRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import timber.log.Timber

class SpeechPageKeyedDataSource(
        private val repository: SpeechRepository,
        private val schedulerProvider: SchedulerProvider,
        private val compositeDisposable: CompositeDisposable,
        private val name: String) : PageKeyedDataSource<Int, Speech>() {
    val networkState: MutableLiveData<NetworkState> = MutableLiveData()
    val numberOfRecords: MutableLiveData<Int> = MutableLiveData()
    val message: MutableLiveData<String> = MutableLiveData()
    val loading: MutableLiveData<Boolean> = MutableLiveData()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Speech>) {
        message.postValue("読み込み中です")
        callApi(1, params.requestedLoadSize) { speeches, next ->
            callback.onResult(speeches, null, next)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Speech>) {
        callApi(params.key, params.requestedLoadSize) { speeches, next ->
            callback.onResult(speeches, next)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Speech>) {
        // 利用しない
    }

    private fun callApi(page: Int, perPage: Int, callback: (speechRecords: List<Speech>, next: Int?) -> Unit) {
        networkState.postValue(NetworkState.RUNNING)
        compositeDisposable.clear()
        repository.getSpeech(page, name)
                .doOnSubscribe { loading.postValue(true) }
                .subscribeOn(schedulerProvider.newThread())
                .compose(ApiErrorTransformer(SpeechData::class.java))
                .observeOn(schedulerProvider.ui())
                .subscribe({ result ->
                    loading.postValue(false)
                    numberOfRecords.postValue(result.numberOfRecords)
                    result.records?.let {
                        callback(it.map { speechRecord -> speechRecord.convert() }, result.nextRecordPosition)
                        Timber.d("callApi:%d", it.size)
                        message.postValue("${result.numberOfRecords}件の発言があります")
                        networkState.postValue(NetworkState.SUCCESS)
                        return@subscribe
                    }
                    result.diagnostics?.let {
                        Timber.e(it[0].message)
                        message.postValue(it[0].message)
                    } ?: let {
                        Timber.e("データがありません")
                        message.postValue("データがありません")
                    }
                    numberOfRecords.postValue(0)
                    networkState.postValue(NetworkState.FAILED)
                }, {
                    Timber.e(it)
                    loading.postValue(false)
                    numberOfRecords.postValue(null)
                    networkState.postValue(NetworkState.FAILED)
                })
                .addTo(compositeDisposable)
    }

    private fun SpeechRecord.convert(): Speech = Speech(
            session = "第${session}回国会",
            nameOfHouse = nameOfHouse,
            nameOfMeeting = nameOfMeeting,
            issue = issue,
            date = date,
            speechOrder = speechOrder,
            speaker = speaker,
            summary = speech
    )
}