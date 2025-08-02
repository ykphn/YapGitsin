package com.ykphn.yapgitsin.presentation.login

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
import com.ykphn.yapgitsin.presentation.login.components.LoginForm
import com.ykphn.yapgitsin.presentation.login.components.LoginHeader
import com.ykphn.yapgitsin.presentation.login.components.SignUpRow
import com.ykphn.yapgitsin.presentation.login.components.SocialLoginRow

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier, navController: NavController
) {
    val viewModel: LoginScreenViewModel = hiltViewModel()
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
            onLoginClick = { navController.navigate("food") })

        SocialLoginRow(onAppleClick = { }, onFacebookClick = { }, onGmailClick = { })

        SignUpRow(onSignUpClick = { navController.navigate("register") })
    }
}

@Preview(showBackground = true, device = "spec:width=1080px,height=2400px,dpi=440")
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()

    LoginScreen(
        navController = navController
    )
}
