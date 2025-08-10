package com.ykphn.yapgitsin.presentation.auth.register

import android.app.Activity
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
import com.ykphn.yapgitsin.presentation.auth.register.components.RegisterForm
import com.ykphn.yapgitsin.presentation.auth.register.components.RegisterHeader
import com.ykphn.yapgitsin.presentation.auth.register.components.RegisterSignInRow
import com.ykphn.yapgitsin.presentation.auth.register.state.RegisterState
import com.ykphn.yapgitsin.presentation.common.screen.LoadingOverlay
import com.ykphn.yapgitsin.presentation.common.screen.LoadingScreen
import com.ykphn.yapgitsin.presentation.main.MainActivity

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val registerState by viewModel.registerState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(registerState) {
        val message = when (registerState) {
            RegisterState.EmailError -> "Geçersiz mail adresi"
            RegisterState.PasswordError -> "Geçersiz şifre"
            RegisterState.GeneralError -> "Bir hata oluştu"
            RegisterState.Success -> "Kullanıcı başarıyla oluşturuludu"
            else -> null
        }

        message?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        if (registerState == RegisterState.Success) {
            val activity = context as? Activity
            context.startActivity(Intent(context, MainActivity::class.java))
            activity?.finish()
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RegisterHeader()

            RegisterForm(
                onRegisterClick = { username, email, password ->
                    viewModel.registerUserAccount(username, email, password)
                }
            )

            RegisterSignInRow(onSignInClick = { navController.navigate("login") })
        }

        if (registerState == RegisterState.Loading) {
            LoadingOverlay()
            LoadingScreen()
        }
    }
}
