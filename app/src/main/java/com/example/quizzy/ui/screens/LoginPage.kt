
package com.example.quizzy.ui.screens

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginPage(
    onLoginSuccess: () -> Unit
) {

    // Firebase Authentication
    val auth = remember {
        FirebaseAuth.getInstance()
    }

    val pink = Color(0xFFFF4FA3)
    val green = Color(0xFF32CB83)
    val darkText = Color(0xFF273340)

    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFFE2F0),
            Color(0xFFF9F5FA),
            Color(0xFFE1FFF0)
        )
    )

    // Form states
    var email by rememberSaveable {
        mutableStateOf("")
    }

    // Do not save passwords in saved instance state
    var password by remember {
        mutableStateOf("")
    }

    var passwordVisible by remember {
        mutableStateOf(false)
    }

    var isCreatingAccount by rememberSaveable {
        mutableStateOf(false)
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }

    // Firebase sign in / create account action
    val submitForm: () -> Unit = {

        val cleanEmail = email.trim()

        errorMessage = when {
            !Patterns.EMAIL_ADDRESS
                .matcher(cleanEmail).matches() ->
                "Please enter a valid email address."

            password.isBlank() ->
                "Please enter your password."

            isCreatingAccount && password.length < 6 ->
                "Password must contain at least 6 characters."

            else -> null
        }

        if (errorMessage == null && !isLoading) {

            isLoading = true

            val task = if (isCreatingAccount) {

                auth.createUserWithEmailAndPassword(
                    cleanEmail,
                    password
                )

            } else {

                auth.signInWithEmailAndPassword(
                    cleanEmail,
                    password
                )
            }

            task.addOnCompleteListener { result ->

                isLoading = false

                if (result.isSuccessful &&
                    auth.currentUser != null
                ) {
                    password = ""
                    onLoginSuccess()
                } else {
                    errorMessage =
                        if (isCreatingAccount) {
                            "Account creation failed. Check your details or try another email."
                        } else {
                            "Sign in failed. Check your credentials and try again."
                        }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .safeDrawingPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Spacer(Modifier.height(24.dp))

        // App title
        Text(
            text = "Quizzy",
            fontSize = 44.sp,
            fontWeight = FontWeight.ExtraBold,
            color = pink
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "LEARN • PLAY • MASTER",
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 2.sp,
            color = darkText,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(36.dp))

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
                    text = if (isCreatingAccount)
                        "Create an account"
                    else
                        "Welcome back!",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = darkText
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = if (isCreatingAccount)
                        "Join Quizzy and start learning."
                    else
                        "Sign in to continue learning.",
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Spacer(Modifier.height(28.dp))

                // Email field
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        errorMessage = null
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Email Address")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Email,
                            contentDescription = null
                        )
                    },
                    singleLine = true,
                    enabled = !isLoading,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = pink,
                        focusedLabelColor = pink
                    )
                )

                Spacer(Modifier.height(16.dp))

                // Password field
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        errorMessage = null
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Password")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Lock,
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                passwordVisible =
                                    !passwordVisible
                            }
                        ) {
                            Icon(
                                imageVector =
                                    if (passwordVisible)
                                        Icons.Rounded.VisibilityOff
                                    else
                                        Icons.Rounded.Visibility,
                                contentDescription =
                                    if (passwordVisible)
                                        "Hide password"
                                    else
                                        "Show password"
                            )
                        }
                    },
                    visualTransformation =
                        if (passwordVisible)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),
                    singleLine = true,
                    enabled = !isLoading,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = pink,
                        focusedLabelColor = pink
                    )
                )

                // Validation / Firebase error
                if (errorMessage != null) {

                    Spacer(Modifier.height(12.dp))

                    Text(
                        text = errorMessage.orEmpty(),
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 13.sp
                    )
                }

                Spacer(Modifier.height(24.dp))

                // Firebase authentication button
                Button(
                    onClick = submitForm,
                    enabled = !isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = green
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {

                    if (isLoading) {

                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )

                    } else {

                        Text(
                            text = if (isCreatingAccount)
                                "Create Account"
                            else
                                "Sign In",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                Spacer(Modifier.height(18.dp))

                TextButton(
                    onClick = {
                        isCreatingAccount = !isCreatingAccount
                        password = ""
                        errorMessage = null
                    },
                    enabled = !isLoading,
                    modifier = Modifier.align(
                        Alignment.CenterHorizontally
                    )
                ) {

                    Text(
                        text = if (isCreatingAccount)
                            "Already have an account? Sign In"
                        else
                            "New to Quizzy? Create Account",
                        color = pink,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Keep learning. Keep growing.",
            fontSize = 13.sp,
            color = darkText,
            textAlign = TextAlign.Center
        )
    }
}
