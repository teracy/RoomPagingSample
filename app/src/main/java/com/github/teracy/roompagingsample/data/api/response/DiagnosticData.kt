package com.github.teracy.roompagingsample.data.api.response

import org.simpleframework.xml.ElementList

/**
 * エラー発生時データ
 */
abstract class DiagnosticData {
    /**
     * エラーメッセージリスト
     */
    @set:ElementList(required = false)
    @get:ElementList(required = false)
    var diagnostics: List<Diagnostic>? = null
}