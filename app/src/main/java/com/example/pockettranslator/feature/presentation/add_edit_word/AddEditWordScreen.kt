package com.example.pockettranslator.feature.presentation.add_edit_word

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pockettranslator.feature.presentation.add_edit_word.components.TransparentHintTextField
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddEditWordScreen(
    navController: NavController,
    viewModel: AddEditWordViewModel = hiltViewModel()
) {
    val originState = viewModel.wordOrigin.value
    val translationState = viewModel.wordTranslation.value

    val scaffoldState = rememberScaffoldState()

    val scope = rememberCoroutineScope()

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
                    viewModel.onEvent(AddEditWordEvent.SaveWord)
                },
                backgroundColor = MaterialTheme.colors.primary
            )  {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save word")
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TransparentHintTextField(
                text = originState.text,
                hint = originState.hint,
                onValueChange = {
                                viewModel.onEvent(AddEditWordEvent.EnteredOrigin(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditWordEvent.ChangeOriginFocus(it))
                },
                isHintVisible = originState.isHintVisible,
                textStyle = MaterialTheme.typography.h5
            )
            TransparentHintTextField(
                text = translationState.text,
                hint = translationState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditWordEvent.EnteredTranslation(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditWordEvent.ChangeTranslationFocus(it))
                },
                isHintVisible = translationState.isHintVisible,
                textStyle = MaterialTheme.typography.h5,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}