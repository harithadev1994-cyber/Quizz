package com.example.quizzy.ui.screens

// Defines the destinations available in Quizzy
sealed interface Routes {

    data object Splash : Routes

    data object Home : Routes

    data object Quiz : Routes

    data object Result : Routes
}
