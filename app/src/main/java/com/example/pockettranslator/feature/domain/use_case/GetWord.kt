package com.example.pockettranslator.feature.domain.use_case

import com.example.pockettranslator.feature.domain.model.Word
import com.example.pockettranslator.feature.domain.repository.WordRepository

class GetWord(
    private val repository: WordRepository
) {

    suspend operator fun invoke(id: Int): Word? {
        return repository.getWordById(id)
    }
}