package com.github.teracy.roompagingsample.data.api.response

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

/**
 * 発言単位検索結果
 */
@Root(name = "data", strict = false)
class SpeechData : DiagnosticData() {
    /**
     * 全体の件数
     */
    @set:Element(required = false)
    @get:Element(required = false)
    var numberOfRecords: Int? = null

    /**
     * 今回の結果に含まれる件数
     */
    @set:Element(required = false)
    @get:Element(required = false)
    var numberOfReturn: Int? = null

    /**
     * 開始位置
     */
    @set:Element(required = false)
    @get:Element(required = false)
    var startRecord: Int? = null

    /**
     * 次の位置（optional）
     */
    @set:Element(required = false)
    @get:Element(required = false)
    var nextRecordPosition: Int? = null

    /**
     * 	検索結果レコードリスト
     */
    @set:ElementList(required = false)
    @get:ElementList(required = false)
    var records: List<SpeechRecord>? = null
}
