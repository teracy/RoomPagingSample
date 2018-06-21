package com.github.teracy.roompagingsample.ui.speech

import com.github.teracy.roompagingsample.data.paging.Speech

interface OnSpeechClickListener {
    fun onClick(speech: Speech?)
}