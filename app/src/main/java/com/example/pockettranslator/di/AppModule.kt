package com.example.pockettranslator.di

import android.app.Application
import androidx.room.Room
import com.example.pockettranslator.feature.data.data_source.WordDatabase
import com.example.pockettranslator.feature.data.repository.WordRepositoryImplementation
import com.example.pockettranslator.feature.domain.repository.WordRepository
import com.example.pockettranslator.feature.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWordDatabase(app: Application): WordDatabase{
        return Room.databaseBuilder(
            app,
            WordDatabase::class.java,
            WordDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideWordRepository(db : WordDatabase): WordRepository {
        return WordRepositoryImplementation(db.wordDao)
    }

    @Provides
    @Singleton
    fun provideWordUseCases(repository: WordRepository): UseCases {
        return UseCases(
            getWords = GetWords(repository),
            deleteWord = DeleteWord(repository),
            addWord = AddWord(repository),
            getWord = GetWord(repository)
        )
    }
}