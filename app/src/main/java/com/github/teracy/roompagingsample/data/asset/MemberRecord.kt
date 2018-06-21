package com.github.teracy.roompagingsample.data.asset

/**
 * 議員レコード
 */
class MemberRecord {
    var name: String = ""
    var kana: String = ""
    var house: String = ""
    var party: String = ""
    // 選挙区
    var constituency: String? = null
    // 比例区
    var block: String? = null
}
