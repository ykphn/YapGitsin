package com.ykphn.yapgitsin.presentation.auth.login

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.ykphn.yapgitsin.presentation.auth.login.state.LoginState
import com.ykphn.yapgitsin.presentation.common.screens.LoadingOverlay
import com.ykphn.yapgitsin.presentation.common.screens.LoadingScreen
import com.ykphn.yapgitsin.presentation.main.MainActivity

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginScreenViewModel = hiltViewModel(),
    navController: NavController,
) {
    val context = LocalContext.current
    val loginState by viewModel.loginState.collectAsState()

    LaunchedEffect(loginState) {
        val message = when (loginState) {
            LoginState.GeneralError -> "Geçersiz kimlik bilgileri."
            LoginState.Success -> "Oturum açıldı"
            else -> null
        }

        message?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        if (loginState == LoginState.Success)
            context.startActivity(Intent(context, MainActivity::class.java))

    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginHeader()

            LoginForm(onLoginClick = { email, password ->
                viewModel.loginUSerAccount(email, password) })

            SocialLoginRow(onAppleClick = { }, onFacebookClick = { }, onGmailClick = { })

            SignUpRow(onSignUpClick = { navController.navigate("register") })
        }

        if (loginState == LoginState.Loading) {
            LoadingOverlay(modifier)
            LoadingScreen(modifier)
        }

    }


}
