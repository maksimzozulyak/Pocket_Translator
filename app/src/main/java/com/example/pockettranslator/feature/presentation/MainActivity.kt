package com.example.pockettranslator.feature.presentation

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pockettranslator.feature.presentation.add_edit_word.AddEditWordScreen
import com.example.pockettranslator.feature.presentation.util.Screen
import com.example.pockettranslator.feature.presentation.words.WordsScreen
import com.example.pockettranslator.ui.theme.PocketTranslatorTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        setContent {
            PocketTranslatorTheme {

                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = Color.Transparent,
                        darkIcons = true
                    )
                }


                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.WordsScreen.route
                    ) {
                        composable(
                            route = Screen.WordsScreen.route
                        ) {
                            WordsScreen(navController = navController)
                        }
                        composable(
                            route = Screen.AddEditWordScreen.route +
                                    "?wordId={wordId}",
                            arguments = listOf(
                                navArgument(
                                    name = "wordId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                            )
                        ) {

                            AddEditWordScreen(
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}
