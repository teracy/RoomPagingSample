package com.github.teracy.roompagingsample.data.repository

import com.github.teracy.roompagingsample.data.api.NdlApiClient
import com.github.teracy.roompagingsample.data.api.response.SpeechData
import com.github.teracy.roompagingsample.data.paging.NDL_PAGE_SIZE
import io.reactivex.Single
import java.net.URLEncoder
import javax.inject.Inject

class SpeechRepository @Inject constructor(private val client: NdlApiClient) {
    // サンプルなのでサイズは30件、開会日付/始点は2018/1/1で固定
    fun getSpeech(startRecord: Int, name: String): Single<SpeechData> {
        // "="もエンコードが必要
        return client.getSpeech("api/1.0/speech?" + URLEncoder.encode("startRecord=$startRecord&maximumRecords=${NDL_PAGE_SIZE}&speaker=$name&from=2018-01-01", "UTF-8"))
    }
}