
package com.example.quizzy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen() {

    val pink = Color(0xFFFF4FA3)
    val green = Color(0xFF32CB83)
    val darkText = Color(0xFF273340)

    // Soft pink and green gradient background
    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFFE2F0),
            Color(0xFFF9F5FA),
            Color(0xFFE1FFF0)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(48.dp))

        // App title
        Text(
            text = "Quizzy",
            fontSize = 42.sp,
            fontWeight = FontWeight.ExtraBold,
            color = pink,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "LEARN • PLAY • MASTER",
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 2.sp,
            color = darkText,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(36.dp))

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
                    color = darkText
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Test your Computer Science knowledge with fun and interactive quizzes.",
                    fontSize = 15.sp,
                    color = Color.DarkGray,
                    lineHeight = 23.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Start Quiz button
                Button(
                    onClick = {
                        // Navigation will be connected
                        // when QuizScreen is implemented.
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = green
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

        Spacer(modifier = Modifier.height(32.dp))

        // Categories heading
        Text(
            text = "Explore Categories",
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            color = darkText,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Computer Science categories
        CategoryCard(
            emoji = "💻",
            title = "Programming"
        )

        CategoryCard(
            emoji = "🧠",
            title = "Data Structures"
        )

        CategoryCard(
            emoji = "⚡",
            title = "Algorithms"
        )

        CategoryCard(
            emoji = "🗄️",
            title = "Databases"
        )

        CategoryCard(
            emoji = "🌐",
            title = "Networking"
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Footer
        Text(
            text = "Keep learning. Keep growing.",
            fontSize = 13.sp,
            color = Color.DarkGray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

// Reusable category card
@Composable
private fun CategoryCard(
    emoji: String,
    title: String
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
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

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = title,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF273340),
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "›",
                fontSize = 28.sp,
                color = Color(0xFFFF4FA3)
            )
        }
    }
}
