package com.example.pockettranslator.feature.presentation.util

sealed class Screen(val route: String) {
    object WordsScreen: Screen("words_screen")
    object AddEditWordScreen: Screen("add_edit_word_screen")
}
