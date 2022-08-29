package com.example.pockettranslator.feature.presentation.words

import com.example.pockettranslator.feature.domain.model.Word

sealed class WordsEvent {
    data class DeleteWord(val word: Word): WordsEvent()
    object RestoreWord : WordsEvent()
}
