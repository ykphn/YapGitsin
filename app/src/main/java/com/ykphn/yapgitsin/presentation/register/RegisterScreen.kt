package com.ykphn.yapgitsin.presentation.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ykphn.yapgitsin.presentation.register.components.RegisterForm
import com.ykphn.yapgitsin.presentation.register.components.RegisterHeader
import com.ykphn.yapgitsin.presentation.register.components.RegisterSignInRow

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val viewModel: RegisterScreenViewModel = hiltViewModel()
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var passwordRepeat by remember { mutableStateOf("") }
    var passwordRepeatVisible by remember { mutableStateOf(false) }
    val passwordsMatch = password == passwordRepeat && password.isNotEmpty()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RegisterHeader()

        RegisterForm(
            username = username,
            onUsernameChange = { username = it },
            email = email,
            onEmailChange = { email = it },
            password = password,
            onPasswordChange = { password = it },
            passwordVisible = passwordVisible,
            onPasswordVisibilityChange = { passwordVisible = !passwordVisible },
            passwordRepeat = passwordRepeat,
            onPasswordRepeatChange = { passwordRepeat = it },
            passwordRepeatVisible = passwordRepeatVisible,
            onPasswordRepeatVisibilityChange = { passwordRepeatVisible = !passwordRepeatVisible },
            onRegisterClick = { }
        )
        RegisterSignInRow(onSignInClick = { navController.navigate("login") })
    }
}

@Preview(showBackground = true, device = "spec:width=1080px,height=2400px,dpi=440")
@Composable
fun RegisterScreenPreview() {
    val navController = rememberNavController()

    RegisterScreen(navController = navController)
}
