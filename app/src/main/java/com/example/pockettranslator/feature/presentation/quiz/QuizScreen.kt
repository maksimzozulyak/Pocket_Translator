package com.example.pockettranslator.feature.presentation.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.SubdirectoryArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pockettranslator.feature.presentation.add_edit_word.AddEditWordViewModel
import com.example.pockettranslator.feature.presentation.add_edit_word.components.TransparentHintTextField
import com.example.pockettranslator.ui.theme.color1
import com.example.pockettranslator.ui.theme.raceGreen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun QuizScreen(
    navController: NavController,
    viewModel: QuizViewModel = hiltViewModel()
) {
    val answerState = viewModel.answerTextField.value
    val word = viewModel.currentWord.value
    val answerColor = viewModel.answerColor.value
    val isAnswerVisible = viewModel.isAnswerVisible.value

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is QuizViewModel.UiEvent.LastNextPressed -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(top = 24.dp)
            .fillMaxHeight()
    ) {

        Button(
            onClick = { navController.navigateUp() },
            modifier = Modifier
                .padding(10.dp)

        )
        {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Go back",
                tint = Color.White
            )
        }

        Column(
            modifier = Modifier
                .padding(24.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colors.surface)
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
        ) {
            Text(
                text = word.translation,
                style = MaterialTheme.typography.body2,
                color = raceGreen,
                modifier = Modifier.padding(top = 16.dp, bottom = 24.dp)
            )
            if (isAnswerVisible) {
                Text(
                    text = word.origin,
                    style = MaterialTheme.typography.body2,
                    color = answerColor,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(24.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.LightGray)
                .fillMaxWidth()
        ) {
            TransparentHintTextField(
                text = answerState.text,
                hint = answerState.hint,
                isHintVisible = answerState.isHintVisible,
                onValueChange = {viewModel.onEvent(QuizEvent.AnswerEntered(it))},
                onFocusChange = {viewModel.onEvent(QuizEvent.AnswerChangeFocus(it))},
                textColor = Color.Black,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp, vertical = 4.dp),
                readOnly = isAnswerVisible,
                textStyle = MaterialTheme.typography.h1
            )
            Button(
                onClick = { viewModel.onEvent(QuizEvent.SubmitPressed) },
                enabled = !isAnswerVisible,
                modifier = Modifier.wrapContentHeight()
            ) {
                Icon(
                    imageVector = Icons.Default.SubdirectoryArrowRight,
                    contentDescription = "Submit",
                    tint = Color.White
                )
            }
        }

        if (isAnswerVisible) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { viewModel.onEvent(QuizEvent.NextPressed) },
                ) {
                    Text(
                        text = "Next",
                        style = MaterialTheme.typography.h1,
                        color = Color.White
                    )
                }
            }
        }

    }
}