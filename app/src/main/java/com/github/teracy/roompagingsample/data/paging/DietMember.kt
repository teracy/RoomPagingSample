package com.github.teracy.roompagingsample.data.paging

/**
 * 表示用議員情報
 */
data class DietMember(
        val id: Int,
        val name: String,
        val kana: String,
        val election: String,
        val party: String
)