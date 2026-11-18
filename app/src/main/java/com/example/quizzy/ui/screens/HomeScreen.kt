
package com.example.quizzy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material.icons.rounded.Code
import androidx.compose.material.icons.rounded.AccountTree
import androidx.compose.material.icons.rounded.Functions
import androidx.compose.material.icons.rounded.Storage
import androidx.compose.material.icons.rounded.Public
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
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
fun HomeScreen(
    onSignOut: () -> Unit
) {

    var selectedTab by rememberSaveable {
        mutableIntStateOf(0)
    }

    var showProfileDialog by rememberSaveable {
        mutableStateOf(false)
    }

    // Get the signed-in Firebase user
    val currentUser = FirebaseAuth.getInstance().currentUser

    val displayName = currentUser?.displayName
        ?.takeIf { it.isNotBlank() }
        ?: "Quizzy Learner"

    val userEmail = currentUser?.email
        ?: "No email available"

    val emailVerified = currentUser?.isEmailVerified == true

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val scope = rememberCoroutineScope()

    val showMessage: (String) -> Unit = { message ->
        scope.launch {
            snackbarHostState.showSnackbar(message)
        }
    }

    val tabs = listOf(
        Triple("Home", Icons.Rounded.Home, 0),
        Triple("Categories", Icons.Rounded.Category, 1),
        Triple("Progress", Icons.Rounded.BarChart, 2)
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),

        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },

        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 6.dp
            ) {

                tabs.forEach { tab ->

                    val isSelected = selectedTab == tab.third

                    NavigationBarItem(
                        selected = isSelected,

                        onClick = {
                            selectedTab = tab.third
                        },

                        icon = {
                            Icon(
                                imageVector = tab.second,
                                contentDescription = tab.first,
                                modifier = Modifier.size(25.dp)
                            )
                        },

                        label = {
                            Text(
                                text = tab.first,
                                fontWeight =
                                    if (isSelected)
                                        FontWeight.SemiBold
                                    else
                                        FontWeight.Normal
                            )
                        },

                        alwaysShowLabel = true,

                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Pink,
                            selectedTextColor = Pink,
                            indicatorColor = Color(0xFFFFE2F0),
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = DarkText
                        )
                    )
                }
            }
        }

    ) { innerPadding ->

        when (selectedTab) {

            0 -> HomeContent(
                modifier = Modifier.padding(innerPadding),

                onProfileClick = {
                    showProfileDialog = true
                },

                onStartQuiz = {
                    showMessage("Quiz feature coming soon!")
                },

                onCategoryClick = { category ->
                    showMessage("$category quiz coming soon!")
                }
            )

            1 -> CategoriesContent(
                modifier = Modifier.padding(innerPadding),

                onCategoryClick = { category ->
                    showMessage("$category quiz coming soon!")
                }
            )

            2 -> ProgressContent(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }

    // Show Firebase account details
    if (showProfileDialog) {

        ProfileDialog(
            displayName = displayName,
            email = userEmail,
            emailVerified = emailVerified,

            onDismiss = {
                showProfileDialog = false
            },

            onSignOut = {
                showProfileDialog = false
                onSignOut()
            }
        )
    }
}

// ------------------------------------
// HOME TAB
// ------------------------------------

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    onProfileClick: () -> Unit,
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

        Spacer(Modifier.height(24.dp))

        // Quizzy header and profile icon
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = "Quizzy",
                    fontSize = 42.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Pink
                )

                Text(
                    text = "LEARN • PLAY • MASTER",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.sp,
                    color = DarkText
                )
            }

            // Profile button at the top right
            IconButton(
                onClick = onProfileClick,
                modifier = Modifier.size(52.dp)
            ) {

                Icon(
                    imageVector = Icons.Rounded.AccountCircle,
                    contentDescription = "Open my account",
                    tint = Pink,
                    modifier = Modifier.size(40.dp)
                )
            }
        }

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

// ------------------------------------
// PROFILE ACCOUNT DIALOG
// ------------------------------------

@Composable
private fun ProfileDialog(
    displayName: String,
    email: String,
    emailVerified: Boolean,
    onDismiss: () -> Unit,
    onSignOut: () -> Unit
) {

    AlertDialog(
        onDismissRequest = onDismiss,

        icon = {
            Icon(
                imageVector = Icons.Rounded.AccountCircle,
                contentDescription = null,
                tint = Pink,
                modifier = Modifier.size(52.dp)
            )
        },

        title = {
            Text(
                text = "My Account",
                fontWeight = FontWeight.Bold,
                color = DarkText
            )
        },

        text = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "Display Name",
                    fontSize = 13.sp,
                    color = Color.Gray
                )

                Text(
                    text = displayName,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkText
                )

                Spacer(Modifier.height(18.dp))

                Text(
                    text = "Email Address",
                    fontSize = 13.sp,
                    color = Color.Gray
                )

                Text(
                    text = email,
                    fontSize = 15.sp,
                    color = DarkText
                )

                Spacer(Modifier.height(18.dp))

                Text(
                    text = "Email Verification",
                    fontSize = 13.sp,
                    color = Color.Gray
                )

                Text(
                    text =
                        if (emailVerified)
                            "Verified"
                        else
                            "Not Verified",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (emailVerified)
                        Green
                    else
                        DarkText
                )
            }
        },

        confirmButton = {
            TextButton(
                onClick = onSignOut
            ) {

                Icon(
                    imageVector = Icons.Rounded.Logout,
                    contentDescription = null,
                    tint = Color(0xFFD32F2F),
                    modifier = Modifier.size(18.dp)
                )

                Spacer(Modifier.width(6.dp))

                Text(
                    text = "Sign Out",
                    color = Color(0xFFD32F2F),
                    fontWeight = FontWeight.SemiBold
                )
            }
        },

        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    text = "Close",
                    color = Pink
                )
            }
        },

        shape = RoundedCornerShape(24.dp),
        containerColor = Color.White
    )
}

// ------------------------------------
// CATEGORIES TAB
// ------------------------------------

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

// ------------------------------------
// PROGRESS TAB
// ------------------------------------

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

            Icon(
                imageVector = Icons.Rounded.BarChart,
                contentDescription = null,
                tint = Pink,
                modifier = Modifier.size(60.dp)
            )

            Spacer(Modifier.height(20.dp))

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

// ------------------------------------
// CATEGORY LIST
// ------------------------------------

@Composable
private fun QuizCategories(
    onCategoryClick: (String) -> Unit
) {

    CategoryCard(
        icon = Icons.Rounded.Code,
        title = "Programming",
        onCategoryClick = onCategoryClick
    )

    CategoryCard(
        icon = Icons.Rounded.AccountTree,
        title = "Data Structures",
        onCategoryClick = onCategoryClick
    )

    CategoryCard(
        icon = Icons.Rounded.Functions,
        title = "Algorithms",
        onCategoryClick = onCategoryClick
    )

    CategoryCard(
        icon = Icons.Rounded.Storage,
        title = "Databases",
        onCategoryClick = onCategoryClick
    )

    CategoryCard(
        icon = Icons.Rounded.Public,
        title = "Networking",
        onCategoryClick = onCategoryClick
    )
}

// ------------------------------------
// REUSABLE CATEGORY CARD
// ------------------------------------

@Composable
private fun CategoryCard(
    icon: ImageVector,
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

            Box(
                modifier = Modifier
                    .size(46.dp)
                    .background(
                        color = Color(0xFFFFE2F0),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Pink,
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(Modifier.width(16.dp))

            Text(
                text = title,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color = DarkText,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector =
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Pink,
                modifier = Modifier.size(26.dp)
            )
        }
    }
}
