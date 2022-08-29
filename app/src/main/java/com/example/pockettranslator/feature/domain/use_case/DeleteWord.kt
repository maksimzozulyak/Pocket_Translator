package com.example.pockettranslator.feature.domain.use_case

import com.example.pockettranslator.feature.domain.model.Word
import com.example.pockettranslator.feature.domain.repository.WordRepository
import kotlinx.coroutines.flow.Flow

class DeleteWord(
    private val repository: WordRepository
) {

    suspend operator fun invoke(word: Word) {
        repository.deleteWord(word)
    }

}