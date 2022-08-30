package com.example.pockettranslator.feature.presentation.add_edit_word

import androidx.compose.ui.focus.FocusState

sealed class AddEditWordEvent {
    data class EnteredOrigin(val value: String): AddEditWordEvent()
    data class ChangeOriginFocus(val focus: FocusState): AddEditWordEvent()
    data class EnteredTranslation(val value: String): AddEditWordEvent()
    data class ChangeTranslationFocus(val focus: FocusState): AddEditWordEvent()
    object SaveWord: AddEditWordEvent()
}