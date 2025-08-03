package com.ykphn.yapgitsin.presentation.auth

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ykphn.yapgitsin.presentation.auth.login.LoginScreen
import com.ykphn.yapgitsin.presentation.auth.register.RegisterScreen

@Composable
fun AuthNavGraph(
    modifier: Modifier = Modifier, navHostController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = "auth",
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { fadeOut() }
    ) {
        navigation(route = "auth", startDestination = "login") {
            composable("login") { LoginScreen(navController = navHostController) }
            composable("register") { RegisterScreen(navController = navHostController) }
            composable("forgotPassword") { }
        }
    }
}