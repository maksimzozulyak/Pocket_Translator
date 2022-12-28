package com.example.pockettranslator.feature.presentation.words

import androidx.compose.ui.focus.FocusState
import com.example.pockettranslator.feature.domain.model.Word
import com.example.pockettranslator.feature.presentation.add_edit_word.WordTextField

sealed class WordsEvent {
    data class DeleteWord(val word: Word): WordsEvent()
    data class SearchBarEntered(val search: String): WordsEvent()
    data class SearchBarChangeFocus(val focus: FocusState): WordsEvent()
    object SearchBarClear : WordsEvent()
    object RestoreWord : WordsEvent()
}
