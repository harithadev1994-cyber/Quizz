
package com.example.quizzy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

private val Pink = Color(0xFFFF4FA3)
private val Green = Color(0xFF32CB83)
private val DarkText = Color(0xFF273340)

private val BackgroundGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFFFFE2F0),
        Color(0xFFF9F5FA),
        Color(0xFFE1FFF0)
    )
)

@Composable
fun HomeScreen() {

    // Currently selected bottom navigation tab
    var selectedTab by rememberSaveable {
        mutableIntStateOf(0)
    }

    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()

    // Message for features we will implement later
    val showMessage: (String) -> Unit = { message ->
        scope.launch {
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),

        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },

        // Bottom navigation bar
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {

                val tabs = listOf(
                    "Home" to "🏠",
                    "Categories" to "📚",
                    "Progress" to "📊"
                )

                tabs.forEachIndexed { index, tab ->

                    NavigationBarItem(
                        selected = selectedTab == index,

                        onClick = {
                            selectedTab = index
                        },

                        icon = {
                            Text(
                                text = tab.second,
                                fontSize = 23.sp
                            )
                        },

                        label = {
                            Text(
                                text = tab.first,
                                fontWeight =
                                    if (selectedTab == index)
                                        FontWeight.Bold
                                    else
                                        FontWeight.Normal
                            )
                        },

                        colors = NavigationBarItemDefaults.colors(
                            selectedTextColor = Pink,
                            indicatorColor = Color(0xFFFFE2F0),
                            unselectedTextColor = DarkText
                        )
                    )
                }
            }
        }

    ) { innerPadding ->

        // Change displayed content when a tab is selected
        when (selectedTab) {

            0 -> HomeContent(
                modifier = Modifier.padding(innerPadding),
                onStartQuiz = {
                    showMessage(
                        "Quiz questions are coming in Sprint 3!"
                    )
                },
                onCategoryClick = { category ->
                    showMessage(
                        "$category quiz is coming soon!"
                    )
                }
            )

            1 -> CategoriesContent(
                modifier = Modifier.padding(innerPadding),
                onCategoryClick = { category ->
                    showMessage(
                        "$category quiz is coming soon!"
                    )
                }
            )

            2 -> ProgressContent(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

// -----------------------------------
// HOME TAB
// -----------------------------------

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    onStartQuiz: () -> Unit,
    onCategoryClick: (String) -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundGradient)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(36.dp))

        Text(
            text = "Quizzy",
            fontSize = 42.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Pink,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "LEARN • PLAY • MASTER",
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 2.sp,
            color = DarkText,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(36.dp))

        // Welcome card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp
            )
        ) {

            Column(
                modifier = Modifier.padding(24.dp)
            ) {

                Text(
                    text = "Ready for a challenge?",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "Test your Computer Science knowledge with fun and interactive quizzes.",
                    fontSize = 15.sp,
                    color = Color.DarkGray,
                    lineHeight = 23.sp
                )

                Spacer(Modifier.height(24.dp))

                Button(
                    onClick = onStartQuiz,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Green
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "Start Quiz",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(Modifier.height(32.dp))

        Text(
            text = "Explore Categories",
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            color = DarkText,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(Modifier.height(16.dp))

        QuizCategories(onCategoryClick)

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Keep learning. Keep growing.",
            fontSize = 13.sp,
            color = Color.DarkGray
        )

        Spacer(Modifier.height(24.dp))
    }
}

// -----------------------------------
// CATEGORIES TAB
// -----------------------------------

@Composable
private fun CategoriesContent(
    modifier: Modifier = Modifier,
    onCategoryClick: (String) -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundGradient)
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Quiz Categories",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Pink
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = "Choose a Computer Science topic to practise.",
            fontSize = 16.sp,
            color = DarkText
        )

        Spacer(Modifier.height(28.dp))

        QuizCategories(onCategoryClick)
    }
}

// -----------------------------------
// PROGRESS TAB
// -----------------------------------

@Composable
private fun ProgressContent(
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundGradient)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "📊",
                fontSize = 56.sp
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Your Progress",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Pink
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "Your quiz scores and achievements will appear here once the quiz feature is ready.",
                fontSize = 16.sp,
                color = DarkText,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )
        }
    }
}

// -----------------------------------
// SHARED CATEGORY LIST
// -----------------------------------

@Composable
private fun QuizCategories(
    onCategoryClick: (String) -> Unit
) {

    CategoryCard("💻", "Programming", onCategoryClick)
    CategoryCard("🧠", "Data Structures", onCategoryClick)
    CategoryCard("⚡", "Algorithms", onCategoryClick)
    CategoryCard("🗄️", "Databases", onCategoryClick)
    CategoryCard("🌐", "Networking", onCategoryClick)
}

// -----------------------------------
// REUSABLE CATEGORY CARD
// -----------------------------------

@Composable
private fun CategoryCard(
    emoji: String,
    title: String,
    onCategoryClick: (String) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .clickable {
                onCategoryClick(title)
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.9f)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = emoji,
                fontSize = 27.sp
            )

            Spacer(Modifier.width(16.dp))

            Text(
                text = title,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color = DarkText,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "›",
                fontSize = 28.sp,
                color = Pink
            )
        }
    }
}
