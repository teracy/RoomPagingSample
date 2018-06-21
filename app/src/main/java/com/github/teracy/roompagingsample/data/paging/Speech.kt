package com.github.teracy.roompagingsample.data.paging

data class Speech(
        val session: String,
        val nameOfHouse: String,
        val nameOfMeeting: String,
        val issue: String,
        val date: String,
        val speechOrder: Int,
        val speaker: String,
        val summary: String
)