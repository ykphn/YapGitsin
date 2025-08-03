package com.ykphn.yapgitsin.presentation.auth.login

import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ykphn.yapgitsin.presentation.auth.login.components.LoginForm
import com.ykphn.yapgitsin.presentation.auth.login.components.LoginHeader
import com.ykphn.yapgitsin.presentation.auth.login.components.SignUpRow
import com.ykphn.yapgitsin.presentation.auth.login.components.SocialLoginRow
import com.ykphn.yapgitsin.presentation.main.MainActivity

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginScreenViewModel = hiltViewModel(),
    navController: NavController,
) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoginHeader()

        LoginForm(
            email = email,
            onEmailChange = { email = it },
            password = password,
            onPasswordChange = { password = it },
            passwordVisible = passwordVisible,
            onPasswordVisibilityChange = { passwordVisible = !passwordVisible },
            onLoginClick = { context.startActivity(Intent(context, MainActivity::class.java)) })

        SocialLoginRow(onAppleClick = { }, onFacebookClick = { }, onGmailClick = { })

        SignUpRow(onSignUpClick = { navController.navigate("register") })
    }
}
