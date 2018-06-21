package com.github.teracy.roompagingsample.data.api

import com.github.teracy.roompagingsample.data.api.response.SpeechData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Url

interface NdlApiService {
    /**
     * 発言単位検索（URL指定）
     */
    @Headers("connection: close")
    @GET
    fun getSpeech(@Url url: String): Single<SpeechData>
}