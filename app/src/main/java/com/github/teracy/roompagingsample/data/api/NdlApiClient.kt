package com.github.teracy.roompagingsample.data.api

import com.github.teracy.roompagingsample.data.api.response.SpeechData
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NdlApiClient @Inject constructor(private val service: NdlApiService) {
    /**
     * 発言単位検索（URL指定）
     */
    fun getSpeech(url: String): Single<SpeechData> {
        return service.getSpeech(url)
    }
}