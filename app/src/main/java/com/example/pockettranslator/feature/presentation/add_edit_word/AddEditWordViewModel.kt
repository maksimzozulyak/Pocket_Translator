package com.example.pockettranslator.feature.presentation.add_edit_word

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pockettranslator.feature.domain.model.Word
import com.example.pockettranslator.feature.domain.use_case.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditWordViewModel @Inject constructor(
    private val useCases: UseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _wordOrigin = mutableStateOf(WordTextFieldState(
        hint = "Enter word"
    ))
    val wordOrigin: State<WordTextFieldState> = _wordOrigin

    private val _wordTranslation = mutableStateOf(WordTextFieldState(
        hint = "Enter translation"
    ))
    val wordTranslation: State<WordTextFieldState> = _wordTranslation

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentWordId: Int? = null

    init {
        savedStateHandle.get<Int>("wordId")?.let { wordId ->
            if(wordId != -1) {
                viewModelScope.launch {
                    useCases.getWord(wordId)?.also { word ->
                        currentWordId = word.id
                        _wordOrigin.value = wordOrigin.value.copy(
                            text = word.origin,
                            isHintVisible = false
                        )
                        _wordTranslation.value = wordTranslation.value.copy(
                            text = word.translation,
                            isHintVisible = false
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditWordEvent) {
        when(event) {
            is AddEditWordEvent.EnteredOrigin -> {
                _wordOrigin.value = wordOrigin.value.copy(
                    text = event.value
                )
            }
            is AddEditWordEvent.ChangeOriginFocus -> {
                _wordOrigin.value = wordOrigin.value.copy(
                    isHintVisible = !event.focus.isFocused &&
                            wordOrigin.value.text.isBlank()
                )
            }
            is AddEditWordEvent.EnteredTranslation -> {
                _wordTranslation.value = wordTranslation.value.copy(
                    text = event.value
                )
            }
            is AddEditWordEvent.ChangeTranslationFocus -> {
                _wordTranslation.value = wordTranslation.value.copy(
                    isHintVisible = !event.focus.isFocused &&
                            wordTranslation.value.text.isBlank()
                )
            }
            is AddEditWordEvent.SaveWord -> {
                viewModelScope.launch {
                    useCases.addWord(
                        Word(
                            origin = wordOrigin.value.text,
                            translation = wordTranslation.value.text,
                            id = currentWordId
                        )
                    )
                    _eventFlow.emit(UiEvent.SaveWord)
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveWord: UiEvent()
    }
}