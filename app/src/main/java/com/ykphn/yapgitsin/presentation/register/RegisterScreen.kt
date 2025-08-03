package com.ykphn.yapgitsin.presentation.register

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ykphn.yapgitsin.presentation.layouts.screens.LoadingOverlay
import com.ykphn.yapgitsin.presentation.layouts.screens.LoadingScreen
import com.ykphn.yapgitsin.presentation.register.components.RegisterForm
import com.ykphn.yapgitsin.presentation.register.components.RegisterHeader
import com.ykphn.yapgitsin.presentation.register.components.RegisterSignInRow
import com.ykphn.yapgitsin.presentation.register.state.RegisterState

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val registerState by viewModel.registerState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(registerState) {
        when (registerState) {
            RegisterState.EmailError -> {
                Toast.makeText(context, "Geçersiz mail adresi", Toast.LENGTH_SHORT).show()
            }
            RegisterState.PasswordError -> {
                Toast.makeText(context, "Geçersiz şifre", Toast.LENGTH_SHORT).show()
            }
            RegisterState.GeneralError -> {
                Toast.makeText(context, "Bir hata oluştu", Toast.LENGTH_SHORT).show()
            }
            RegisterState.Success -> {
                Toast.makeText(context, "Kullanıcı başarıyla oluşturulud", Toast.LENGTH_SHORT).show()
                navController.navigate("login")
            }
            else -> {}
        }
    }

//    Column(
//        modifier = modifier
//            .fillMaxSize()
//            .padding(24.dp),
//        verticalArrangement = Arrangement.SpaceEvenly,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        RegisterHeader()
//
//        RegisterForm(
//            onRegisterClick = { username, email, password ->
//                viewModel.registerUserAccount(email, password)
//            }
//        )
//        RegisterSignInRow(onSignInClick = { navController.navigate("login") })
//    }
    Box(modifier = modifier.fillMaxSize()) {

        // UI içeriği (soluklaşıyor)
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

        // Yarı saydam clickable layer (tıklamayı engellemek için)
        if (registerState == RegisterState.Loading) {
            LoadingOverlay()
            LoadingScreen()
        }
    }
}

@Preview(showBackground = true, device = "spec:width=1080px,height=2400px,dpi=440")
@Composable
fun RegisterScreenPreview() {
    val navController = rememberNavController()
    RegisterScreen(navController = navController)
}
