package com.example.pockettranslator.feature.presentation.add_edit_word

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pockettranslator.feature.domain.model.Word
import com.example.pockettranslator.feature.domain.use_case.UseCases
import com.example.pockettranslator.feature.presentation.util.CustomTextFieldState
import com.example.pockettranslator.ui.theme.colorsList
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

    private val _wordOrigin = mutableStateOf(
        CustomTextFieldState(
        hint = "Enter word"
    )
    )
    val wordOrigin: State<CustomTextFieldState> = _wordOrigin

    private val _wordTranslation = mutableStateOf(
        CustomTextFieldState(
        hint = "Enter translation"
    )
    )
    val wordTranslation: State<CustomTextFieldState> = _wordTranslation

    private val _wordExamplesField = mutableStateOf(
        CustomTextFieldState(
        hint = "Enter example"
    )
    )
    val wordExamplesField: State<CustomTextFieldState> = _wordExamplesField

    private val _wordExamples = mutableStateListOf<String>()
    val wordExample : SnapshotStateList<String> = _wordExamples

    var color: Int = colorsList.random().toArgb()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var isRemembered = mutableStateOf(false)

    private var currentWordId: Int? = null

    init {
        savedStateHandle.get<Int>("wordId")?.let { wordId ->
            if(wordId != -1) {
                viewModelScope.launch {
                    useCases.getWord(wordId)?.also { word ->
                        color = word.color
                        currentWordId = word.id
                        _wordOrigin.value = wordOrigin.value.copy(
                            text = word.origin,
                            isHintVisible = false
                        )
                        _wordTranslation.value = wordTranslation.value.copy(
                            text = word.translation,
                            isHintVisible = false
                        )
                        _wordExamples.clear()
                        _wordExamples.addAll(word.examples)
                        isRemembered.value = true
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditWordEvent) {
        when(event) {
            is AddEditWordEvent.Entered -> {
                when (event.wordTextField) {
                    WordTextField.Origin -> {
                        _wordOrigin.value = wordOrigin.value.copy(
                            text = event.value
                        )
                    }
                    WordTextField.Translation -> {
                        _wordTranslation.value = wordTranslation.value.copy(
                            text = event.value
                        )
                    }
                    WordTextField.Examples -> {
                        _wordExamplesField.value = wordExamplesField.value.copy(
                            text = event.value
                        )
                    }
                }
            }
            is AddEditWordEvent.ChangeFocus -> {
                when (event.wordTextField) {
                    WordTextField.Origin -> {
                        _wordOrigin.value = wordOrigin.value.copy(
                            isHintVisible = !event.focus.isFocused &&
                                    wordOrigin.value.text.isBlank()
                        )
                    }
                    WordTextField.Translation -> {
                        _wordTranslation.value = wordTranslation.value.copy(
                            isHintVisible = !event.focus.isFocused &&
                                    wordTranslation.value.text.isBlank()
                        )
                    }
                    WordTextField.Examples -> {
                        _wordExamplesField.value = wordExamplesField.value.copy(
                            isHintVisible = !event.focus.isFocused &&
                                    wordExamplesField.value.text.isBlank()
                        )
                    }
                }
            }
            is AddEditWordEvent.SaveWord -> {

                if (wordExamplesField.value.text.isNotBlank()) {
                    _wordExamples.add(wordExamplesField.value.text)
                    _wordExamplesField.value = wordExamplesField.value.copy(
                        text = ""
                    )
                }

                viewModelScope.launch {
                    useCases.addWord(
                        Word(
                            origin = wordOrigin.value.text,
                            translation = wordTranslation.value.text,
                            color = color,
                            id = currentWordId,
                            examples = wordExample,
                            isRemembered = isRemembered.value
                        )
                    )
                    if (event.goBack) {
                        _eventFlow.emit(UiEvent.SaveWord)
                    }
                }
            }
            is AddEditWordEvent.AddExample -> {
                _wordExamples.add(wordExamplesField.value.text)
                _wordExamplesField.value = wordExamplesField.value.copy(
                    text = ""
                )
            }
            is AddEditWordEvent.RemoveExample -> {
                if (!wordExample.isEmpty()) {
                    _wordExamples.removeLast()
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveWord: UiEvent()
    }
}