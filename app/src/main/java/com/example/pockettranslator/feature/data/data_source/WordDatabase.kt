package com.example.pockettranslator.feature.data.data_source

import android.annotation.SuppressLint
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pockettranslator.feature.domain.model.Word

@Database(
    entities = [Word::class],
    version = 1
)
@TypeConverters(DataConverter::class)
abstract class WordDatabase: RoomDatabase() {

    abstract val wordDao: WordDao

    companion object {
        const val DATABASE_NAME = "words_db"
    }
}