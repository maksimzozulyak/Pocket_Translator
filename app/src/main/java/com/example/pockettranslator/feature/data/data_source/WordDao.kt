package com.example.pockettranslator.feature.data.data_source

import androidx.room.*
import com.example.pockettranslator.feature.domain.model.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    @Query("SELECT * FROM word " +
            "WHERE (origin like '%' || :search || '%') or (translation like '%' || :search || '%') ")
    fun getWords(search: String = ""): Flow<List<Word>>

    @Query("SELECT * FROM word WHERE id = :id")
    suspend fun getWordById(id: Int): Word?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: Word)

    @Delete
    suspend fun deleteWord(word: Word)
}