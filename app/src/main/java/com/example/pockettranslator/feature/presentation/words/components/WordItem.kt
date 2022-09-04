package com.example.pockettranslator.feature.presentation.words.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.example.pockettranslator.feature.domain.model.Word
import com.example.pockettranslator.ui.theme.Purple200
import com.example.pockettranslator.ui.theme.Teal200


@Composable
fun WordItem(
    word: Word,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 12.dp,
    cutCornerSize: Dp = 30.dp
    ) {
    Box(
        modifier = modifier,
    ) {
        Canvas(modifier = Modifier.matchParentSize()){
            val clipPath = Path().apply {
                lineTo(size.width - cutCornerSize.toPx(), 0f)
                lineTo(size.width, cutCornerSize.toPx())
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }

            clipPath(clipPath) {
                drawRoundRect(
                    color = Purple200,
                    size = size,
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
                drawRoundRect(
                    color = Color(
                        ColorUtils.blendARGB(Teal200.toArgb(), 0x000000, 0.2f)
                    ),
                    topLeft = Offset(size.width - cutCornerSize.toPx(), -100f),
                    size = Size(
                        cutCornerSize.toPx() + 100f, cutCornerSize.toPx() + 100f
                    ),
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = word.origin,
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colors.onSurface,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = word.translation,
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colors.onSurface,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}