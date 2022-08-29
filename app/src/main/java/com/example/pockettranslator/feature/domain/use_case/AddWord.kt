package com.example.pockettranslator.feature.domain.use_case

import com.example.pockettranslator.feature.domain.model.Word
import com.example.pockettranslator.feature.domain.repository.WordRepository

class AddWord (
    private val repository: WordRepository
) {

    suspend operator fun invoke(word: Word) {
        //autotransl
        repository.insertWord(word)
    }
}