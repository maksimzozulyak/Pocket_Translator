package com.example.pockettranslator.feature.presentation.quiz

import androidx.compose.ui.focus.FocusState
import com.example.pockettranslator.feature.domain.model.Word

sealed class QuizEvent {
    data class AnswerEntered(val answer: String): QuizEvent()
    data class AnswerChangeFocus(val focus: FocusState): QuizEvent()
    object SubmitPressed: QuizEvent()
    object NextPressed: QuizEvent()
}