package com.example.pockettranslator.feature.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Word(
    val origin: String,
    val translation: String,
    @PrimaryKey val id: Int? = null
)
