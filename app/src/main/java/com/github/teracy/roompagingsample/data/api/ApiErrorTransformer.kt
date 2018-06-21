package com.github.teracy.roompagingsample.data.api

import com.github.teracy.roompagingsample.data.api.response.DiagnosticData
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.SingleTransformer
import org.simpleframework.xml.core.Persister
import retrofit2.HttpException

/**
 * レスポンスコード400で返ってくるエラー発生時データを取得するためのSingleTransformer
 */
class ApiErrorTransformer<T : DiagnosticData>(private val clazz: Class<T>) : SingleTransformer<T, T> {
    override fun apply(upstream: Single<T>): SingleSource<T> {
        return upstream.onErrorResumeNext {
            if (it is HttpException) {
                return@onErrorResumeNext Single.just(Persister().read(clazz, it.response().errorBody()?.charStream()))
            }
            throw IllegalStateException(it)
        }
    }
}