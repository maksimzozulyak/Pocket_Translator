package com.example.pockettranslator.feature.domain.use_case

import com.example.pockettranslator.feature.domain.model.Word
import com.example.pockettranslator.feature.domain.repository.WordRepository
import kotlinx.coroutines.flow.Flow

class GetWords(
    private val repository: WordRepository
) {

    operator fun invoke(): Flow<List<Word>> {
        return repository.getWords()
    }

}