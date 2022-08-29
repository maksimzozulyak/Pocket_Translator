package com.example.pockettranslator.feature.data.repository

import com.example.pockettranslator.feature.data.data_source.WordDao
import com.example.pockettranslator.feature.domain.model.Word
import com.example.pockettranslator.feature.domain.repository.WordRepository
import kotlinx.coroutines.flow.Flow

class WordRepositoryImplementation(
    private val dao: WordDao
): WordRepository {
    override fun getWords(): Flow<List<Word>> {
        return dao.getWords()
    }

    override suspend fun getWordById(id: Int): Word? {
        return dao.getWordById(id)
    }

    override suspend fun insertWord(word: Word) {
        dao.insertWord(word)
    }

    override suspend fun deleteWord(word: Word) {
        dao.deleteWord(word)
    }
}