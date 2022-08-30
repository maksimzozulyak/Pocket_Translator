package com.example.pockettranslator.feature.presentation.words

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pockettranslator.feature.domain.model.Word
import com.example.pockettranslator.feature.presentation.words.components.WordItem
import kotlinx.coroutines.launch

@Composable
fun WordsScreen(
    navController: NavController,
    viewModel: WordsViewModel = hiltViewModel()
) {
    val list = viewModel.list.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {


                },
            backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add word")
            }
        },
        scaffoldState = scaffoldState,
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(list) { word: Word ->  
                WordItem(
                    word = word, 
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { },
                    onDeleteWord = {
                        viewModel.onEvent(WordsEvent.DeleteWord(word))
                        scope.launch { 
                            val result = scaffoldState.snackbarHostState.showSnackbar(
                                message = "Word deleted",
                                actionLabel = "Undo"
                            )
                            if(result == SnackbarResult.ActionPerformed) {
                                viewModel.onEvent(WordsEvent.RestoreWord)
                            }
                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}