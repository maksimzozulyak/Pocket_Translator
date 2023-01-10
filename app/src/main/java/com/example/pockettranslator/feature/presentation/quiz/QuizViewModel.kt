package com.example.pockettranslator.feature.presentation.quiz

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pockettranslator.feature.domain.model.Word
import com.example.pockettranslator.feature.domain.use_case.UseCases
import com.example.pockettranslator.feature.presentation.add_edit_word.AddEditWordViewModel
import com.example.pockettranslator.feature.presentation.util.CustomTextFieldState
import com.example.pockettranslator.feature.presentation.words.WordsEvent
import com.example.pockettranslator.ui.theme.wrongRed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    private val _list = mutableStateOf(listOf<Word>())
    val list: State<List<Word>> = _list

    private val _currentWord = mutableStateOf(Word("",""))
    val currentWord: State<Word> = _currentWord

    private var id = 0

    val answerColor = mutableStateOf(Color.Red)

    private lateinit var job : Job

    val isAnswerVisible = mutableStateOf(false)

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _answerTextField = mutableStateOf(
        CustomTextFieldState(
            hint = "Enter translation"
        )
    )
    val answerTextField: State<CustomTextFieldState> = _answerTextField

    init {
        getWords()
    }

    fun onEvent(event: QuizEvent) {
        when (event) {
            is QuizEvent.AnswerEntered -> {
                _answerTextField.value = answerTextField.value.copy(
                    text = event.answer
                )
            }
            is QuizEvent.AnswerChangeFocus -> {
                _answerTextField.value = answerTextField.value.copy(
                    isHintVisible = !event.focus.isFocused &&
                            answerTextField.value.text.isBlank()
                )
            }
            is QuizEvent.SubmitPressed -> {

                job.cancel()

                if (answerTextField.value.text.isNotBlank()) {
                    answerColor.value =
                        if (currentWord.value.origin.lowercase(Locale.getDefault()) == answerTextField.value.text.lowercase(Locale.getDefault())) Color.Green else wrongRed
                    isAnswerVisible.value = true

                    viewModelScope.launch {
                        useCases.addWord(
                            currentWord.value.copy(
                                isRemembered = currentWord.value.origin == answerTextField.value.text
                            )
                        )
                    }
                }
            }
            is QuizEvent.NextPressed -> {
                isAnswerVisible.value = false
                id++
                if (list.value.size != id) {
                    _currentWord.value = list.value.get(id)
                    _answerTextField.value = answerTextField.value.copy(
                        text = ""
                    )
                } else {
                    viewModelScope.launch {
                        _eventFlow.emit(UiEvent.LastNextPressed)
                    }
                }
            }
        }
    }

    private fun getWords() {
        job = viewModelScope.launch {
            useCases.getWords().cancellable()
                .collect { words ->
                    Log.d("abama", words.toString())
                    _list.value = words.mapIndexedNotNull { index, it ->
                        if (!it.isRemembered) {
                            it
                        } else {
                            if (Random.nextInt(0, 6) == 5 || index == Random.nextInt(0, words.size)) {
                                it
                            } else {
                                null
                            }
                        }
                    }.shuffled()
                    _currentWord.value = list.value.first()
                }
        }
    }

    sealed class UiEvent {
        object LastNextPressed: UiEvent()
    }
}