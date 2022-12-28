package com.example.pockettranslator.feature.presentation.add_edit_word

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pockettranslator.feature.presentation.add_edit_word.components.AddRemoveExampleButton
import com.example.pockettranslator.feature.presentation.add_edit_word.components.TransparentHintTextField
import com.example.pockettranslator.ui.theme.darkGreen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddEditWordScreen(
    navController: NavController,
    viewModel: AddEditWordViewModel = hiltViewModel()
) {
    val originState = viewModel.wordOrigin.value
    val translationState = viewModel.wordTranslation.value
    val examplesTextFieldState = viewModel.wordExamplesField.value
    val color = viewModel.color
    val exampleList = viewModel.wordExample
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is AddEditWordViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is AddEditWordViewModel.UiEvent.SaveWord -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditWordEvent.SaveWord(true))
                },
                backgroundColor = darkGreen,
                modifier = Modifier.padding(bottom = 34.dp)
            )  {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = "Save word",
                    tint = Color.White
                )
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .background(Color(color))
                .fillMaxSize()
                .padding(16.dp, top = 46.dp)
        ) {
            TransparentHintTextField(
                text = originState.text,
                hint = originState.hint,
                onValueChange = {
                                viewModel.onEvent(AddEditWordEvent.Entered(WordTextField.Origin,it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditWordEvent.ChangeFocus(WordTextField.Origin,it))
                },
                isHintVisible = originState.isHintVisible,
                textStyle = MaterialTheme.typography.h1
            )
            TransparentHintTextField(
                text = translationState.text,
                hint = translationState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditWordEvent.Entered(WordTextField.Translation,it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditWordEvent.ChangeFocus(WordTextField.Translation,it))
                },
                isHintVisible = translationState.isHintVisible,
                textStyle = MaterialTheme.typography.h1
            )
            Divider(
                color = MaterialTheme.colors.primary,
                thickness = 3.dp,
                modifier = Modifier.padding(horizontal = 64.dp)
            )
            Box(modifier = Modifier
                .padding(
                    top = 20.dp,
                    start = 12.dp,
                    end = 12.dp,
                    bottom = 6.dp
                )
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(MaterialTheme.colors.secondary)
            ) {
                Column {

                    TransparentHintTextField(
                        text = examplesTextFieldState.text,
                        hint = examplesTextFieldState.hint,
                        onValueChange = {
                            viewModel.onEvent(AddEditWordEvent.Entered(WordTextField.Examples, it))
                        },
                        onFocusChange = {
                            viewModel.onEvent(
                                AddEditWordEvent.ChangeFocus(
                                    WordTextField.Examples,
                                    it
                                )
                            )
                        },
                        isHintVisible = examplesTextFieldState.isHintVisible,
                        textStyle = MaterialTheme.typography.h1,
                    )

                    if (!exampleList.isEmpty()) {
                        LazyColumn(
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colors.primary)
                                .padding(vertical = 8.dp)
                        ) {

                            itemsIndexed(
                                items = exampleList,
                                key = { index, item ->
                                    item.hashCode() - index.hashCode()
                                }
                            ) { index, example ->

                                Text(
                                    text = example
                                )

                                if (index != exampleList.size - 1) {
                                    Divider(
                                        color = MaterialTheme.colors.secondary,
                                        thickness = 3.dp,
                                        modifier = Modifier.padding(vertical = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                AddRemoveExampleButton(
                    onClick = { viewModel.onEvent(AddEditWordEvent.RemoveExample) },
                    icon = Icons.Default.Remove
                )
                Spacer(modifier = Modifier.width(20.dp))
                AddRemoveExampleButton(
                    onClick = { viewModel.onEvent(AddEditWordEvent.AddExample) },
                    icon = Icons.Default.Add
                )
            }
        }
    }
}