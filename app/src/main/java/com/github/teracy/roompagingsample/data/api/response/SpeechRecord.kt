package com.github.teracy.roompagingsample.data.api.response

import org.simpleframework.xml.Element
import org.simpleframework.xml.Path
import org.simpleframework.xml.Root

/**
 * 発言単位検索結果の検索結果レコード
 */
@Root(name = "record", strict = false)
class SpeechRecord {
    @get:Path("recordData/speechRecord")
    @set:Element
    @get:Element
    var session: Int = 1

    @get:Path("recordData/speechRecord")
    @set:Element
    @get:Element
    var nameOfHouse: String = ""

    @get:Path("recordData/speechRecord")
    @set:Element
    @get:Element
    var nameOfMeeting: String = ""

    @get:Path("recordData/speechRecord")
    @set:Element
    @get:Element
    var issue: String = ""

    @get:Path("recordData/speechRecord")
    @set:Element
    @get:Element
    var date: String = ""

    @get:Path("recordData/speechRecord")
    @set:Element
    @get:Element
    var speechOrder: Int = 1

    @get:Path("recordData/speechRecord")
    @set:Element
    @get:Element
    var speaker: String = ""

    @get:Path("recordData/speechRecord")
    @set:Element
    @get:Element
    var speech: String = ""

    @get:Path("recordData/speechRecord")
    @set:Element(name = "meetingURL")
    @get:Element(name = "meetingURL")
    var meetingUrl: String = ""

    @get:Path("recordData/speechRecord")
    @set:Element(name = "pdfURL")
    @get:Element(name = "pdfURL")
    var pdfUrl: String = ""
}