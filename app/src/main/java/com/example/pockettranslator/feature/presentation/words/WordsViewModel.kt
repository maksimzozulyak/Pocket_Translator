package com.example.pockettranslator.feature.presentation.words

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pockettranslator.feature.domain.model.Word
import com.example.pockettranslator.feature.domain.use_case.UseCases
import com.example.pockettranslator.feature.presentation.util.CustomTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordsViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    private val _list = mutableStateOf(listOf<Word>())
    val list: State<List<Word>> = _list

    private var getWordsJob: Job? = null

    private var recentlyDeletedWord: Word? = null

    private val _searchBar = mutableStateOf(
        CustomTextFieldState(
            hint = "Find"
        )
    )
    val searchBar: State<CustomTextFieldState> = _searchBar

    init {
        getWords()
    }

    fun onEvent(event: WordsEvent) {
        when(event) {
            is WordsEvent.DeleteWord ->{
                viewModelScope.launch {
                    useCases.deleteWord(event.word)
                    recentlyDeletedWord = event.word
                }
            }
            is WordsEvent.RestoreWord ->{
                viewModelScope.launch {
                    useCases.addWord(recentlyDeletedWord?: return@launch)
                    recentlyDeletedWord = null
                }
            }
            is WordsEvent.SearchBarEntered ->{
                _searchBar.value = searchBar.value.copy(
                    text = event.search
                )
                getWords(search = searchBar.value.text)
            }
            is WordsEvent.SearchBarChangeFocus ->{
                _searchBar.value = searchBar.value.copy(
                    isHintVisible = !event.focus.isFocused &&
                            searchBar.value.text.isBlank()
                )
            }
            is WordsEvent.SearchBarClear ->{
                _searchBar.value = searchBar.value.copy(
                    text = ""
                )
                getWords()
            }
        }
    }

    private fun getWords(search: String = "") {
        getWordsJob?.cancel()
        getWordsJob = useCases.getWords(search)
            .onEach { words ->
                _list.value = words
            }
            .launchIn(viewModelScope)
    }
}