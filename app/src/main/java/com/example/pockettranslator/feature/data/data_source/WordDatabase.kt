package com.example.pockettranslator.feature.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pockettranslator.feature.domain.model.Word

@Database(
    entities = [Word::class],
    version = 1
)
abstract class WordDatabase: RoomDatabase() {

    abstract val wordDao: WordDao

    companion object {
        const val DATABASE_NAME = "words_db"
    }
}