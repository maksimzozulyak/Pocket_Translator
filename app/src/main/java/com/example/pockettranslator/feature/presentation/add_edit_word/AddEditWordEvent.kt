package com.example.pockettranslator.feature.presentation.add_edit_word

import androidx.compose.ui.focus.FocusState

sealed class AddEditWordEvent {
    data class Entered(val wordTextField : WordTextField, val value: String): AddEditWordEvent()
    data class ChangeFocus(val wordTextField : WordTextField, val focus: FocusState): AddEditWordEvent()
    object AddExample: AddEditWordEvent()
    object RemoveExample: AddEditWordEvent()
    data class SaveWord(val goBack : Boolean): AddEditWordEvent()
}

enum class WordTextField {
    Origin, Translation, Examples;
}