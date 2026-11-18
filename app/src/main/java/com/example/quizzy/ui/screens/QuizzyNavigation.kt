
package com.example.quizzy.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.google.firebase.auth.FirebaseAuth

@Composable
fun QuizzyNavigation() {

    // Firebase Authentication instance
    val auth = remember {
        FirebaseAuth.getInstance()
    }

    // Navigation back stack
    val backStack = remember {
        mutableStateListOf<Routes>(Routes.Splash)
    }

    NavDisplay(
        backStack = backStack,

        // Handle Android Back button
        onBack = {
            if (backStack.size > 1) {
                backStack.removeAt(backStack.lastIndex)
            }
        },

        // Navigation destinations
        entryProvider = { route ->

            when (route) {

                // -------------------------
                // SPLASH SCREEN
                // -------------------------

                Routes.Splash -> NavEntry(route) {

                    SplashScreen(
                        onFinished = {

                            // Remove Splash from navigation history
                            backStack.clear()

                            // Check existing Firebase session
                            if (auth.currentUser != null) {

                                // User already signed in
                                backStack.add(Routes.Home)

                            } else {

                                // User needs to sign in
                                backStack.add(Routes.Login)
                            }
                        }
                    )
                }

                // -------------------------
                // LOGIN SCREEN
                // -------------------------

                Routes.Login -> NavEntry(route) {

                    LoginPage(
                        onLoginSuccess = {

                            // Login successfully verified by Firebase
                            backStack.clear()

                            // Navigate to Home screen
                            backStack.add(Routes.Home)
                        }
                    )
                }

                // -------------------------
                // HOME SCREEN
                // -------------------------

                Routes.Home -> NavEntry(route) {

                    HomeScreen()
                }

                // -------------------------
                // QUIZ SCREEN
                // -------------------------

                Routes.Quiz -> NavEntry(route) {

                    Text(
                        text = "Quiz Screen Coming Soon"
                    )
                }

                // -------------------------
                // RESULT SCREEN
                // -------------------------

                Routes.Result -> NavEntry(route) {

                    Text(
                        text = "Result Screen Coming Soon"
                    )
                }
            }
        }
    )
}
