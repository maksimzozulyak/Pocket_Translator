package com.example.pockettranslator.feature.presentation.words.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchBar(
    text: String,
    hint: String,
    textStyle: TextStyle = TextStyle(),
    modifier: Modifier = Modifier,
    isHintVisible: Boolean = true,
    onValueChange: (String) -> Unit,
    onFocusChange: (FocusState) -> Unit,
    onClearPressed: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.LightGray, shape = RoundedCornerShape(18.dp))
            .wrapContentHeight(Alignment.CenterVertically),
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search",
            tint = Color.Black,
            modifier = Modifier.fillMaxHeight()
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .padding(top = 2.dp)
                .fillMaxSize()
        ) {
            BasicTextField(
                value = text,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = textStyle,
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        onFocusChange(it)
                    }
            )
            if(isHintVisible) {
                Text(
                    text = hint,
                    style = textStyle
                )
            }
        }
        if (text.isNotBlank()) {
            Button(
                onClick = onClearPressed,
                shape = CircleShape,
                modifier = Modifier
                    .size(35.dp)
                    .padding(7.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
                contentPadding = PaddingValues(2.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Clear",
                    tint = Color.White,
                )
            }
        }
    }
}