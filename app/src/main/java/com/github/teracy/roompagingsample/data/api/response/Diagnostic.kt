package com.github.teracy.roompagingsample.data.api.response

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

/**
 * エラーメッセージ
 */
@Root(name = "diagnostic", strict = false)
class Diagnostic {
    /**
     * メッセージ内容
     */
    @set:Element
    @get:Element
    var message: String = ""

    /**
     * 詳細情報
     */
    @set:Element(required = false)
    @get:Element(required = false)
    var details: String? = null
}