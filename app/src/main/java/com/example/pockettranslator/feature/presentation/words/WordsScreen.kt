package com.example.pockettranslator.feature.presentation.words

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pockettranslator.feature.domain.model.Word
import com.example.pockettranslator.feature.presentation.util.Screen
import com.example.pockettranslator.feature.presentation.words.components.WordItem
import com.example.pockettranslator.ui.theme.darkGreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
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
                    navController.navigate(Screen.AddEditWordScreen.route)
                },
            backgroundColor = darkGreen
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add word",
                    tint = Color.White
                )
            }
        },
        scaffoldState = scaffoldState,
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxSize()
        ) {

            itemsIndexed(
                items = list,
                key = {index, item ->
                    item.hashCode()
                }
            ) { index, word ->

                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToEnd){
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
                        true
                    }
                )

                SwipeToDismiss(
                    modifier = Modifier
                        .animateItemPlacement()
                        .padding(horizontal = 6.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    state = dismissState,
                    background = {
                        val color by animateColorAsState(
                            when (dismissState.targetValue) {
                                DismissValue.Default -> Color.White
                                else -> Color.Red
                            }
                        )

                        val scale by animateFloatAsState(
                            if (dismissState.targetValue == DismissValue.Default) 0.5f else 1f
                        )
                        Box(
                            Modifier
                                .padding(6.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(color)
                                .fillMaxSize(),
                            contentAlignment = Alignment.CenterStart,
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete Icon",
                                modifier = Modifier.scale(scale)
                            )
                        }
                    },
                    dismissContent = {
                        Card(
                            elevation = animateDpAsState(
                                if (dismissState.dismissDirection != null) 4.dp else 0.dp
                            ).value
                        ) {
                            WordItem(
                                word = word,
                                modifier = Modifier
                                    .padding()
                                    .background(Color.White)
                                    .fillMaxWidth()
                                    .clickable {
                                        navController.navigate(
                                            Screen.AddEditWordScreen.route +
                                                    "?wordId=${word.id}"
                                        )
                                    }
                            )
                        }
                    },
                    directions = setOf(DismissDirection.StartToEnd),
                    dismissThresholds = { FractionalThreshold(0.3f) }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}