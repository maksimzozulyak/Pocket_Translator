package com.example.pockettranslator.feature.domain.use_case

import com.example.pockettranslator.feature.domain.model.Word
import com.example.pockettranslator.feature.domain.model.translate
import com.example.pockettranslator.feature.domain.repository.WordRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddWord (
    private val repository: WordRepository
) {

    suspend operator fun invoke(word: Word) {

        if (word.translation.isBlank()) {

            if (word.origin.isBlank()) {

                repository.insertWord(word)

            } else {
                GlobalScope.launch {
                    repository.insertWord(word.copy(translation = translate(word.origin)))
                }
            }
        } else {
            repository.insertWord(word)
        }
    }
}