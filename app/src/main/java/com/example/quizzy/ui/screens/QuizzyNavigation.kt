package com.example.quizzy.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay

@Composable
fun QuizzyNavigation() {

    val backStack = remember {
        mutableStateListOf<Routes>(Routes.Splash)
    }

    NavDisplay(
        backStack = backStack,

        onBack = {
            if (backStack.size > 1) {
                backStack.removeAt(backStack.lastIndex)
            }
        },

        entryProvider = { route ->

            when (route) {

                Routes.Splash -> NavEntry(route) {
                    SplashScreen(
                        onFinished = {
                            backStack.clear()
                            backStack.add(Routes.Home)
                        }
                    )
                }

                Routes.Home -> NavEntry(route) {
                    HomeScreen()
                }

                else -> NavEntry(route) {
                    // Quiz and Result screens
                    // will be implemented later.
                }
            }
        }
    )
}
