package com.example.pockettranslator.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        letterSpacing = 0.sp
    ),
    body2 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 42.sp,
        letterSpacing = 0.sp
    ),
    h1 = TextStyle(
        fontSize = 30.sp
    ),
    h2 = TextStyle(
        fontSize = 42.sp,
        color = Color.White
    ),
    button = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        letterSpacing = 1.25.sp
    ),
)