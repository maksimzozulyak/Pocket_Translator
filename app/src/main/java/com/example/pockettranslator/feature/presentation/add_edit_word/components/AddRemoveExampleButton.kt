package com.example.pockettranslator.feature.presentation.add_edit_word.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun AddRemoveExampleButton(
    onClick: () -> Unit,
    icon: ImageVector
) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        modifier = Modifier
            .size(50.dp)
    )  {
        Icon(
            imageVector = icon,
            contentDescription = "Change examples",
            tint = Color.White
        )
    }
}