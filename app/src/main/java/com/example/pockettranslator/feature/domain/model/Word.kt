package com.example.pockettranslator.feature.domain.model

import android.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Word(
    val origin: String,
    val translation: String,
    val color: Int = Color.WHITE,
    val examples: List<String> = listOf(""),
    val isRemembered: Boolean = false,
    @PrimaryKey val id: Int? = null
)
