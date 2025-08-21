package com.ykphn.yapgitsin.presentation.main

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.ykphn.yapgitsin.presentation.main.screens.FoodListScreen
import com.ykphn.yapgitsin.presentation.main.screens.EditProfileScreen
import com.ykphn.yapgitsin.presentation.main.screens.ProfileScreen
import com.ykphn.yapgitsin.presentation.main.screens.ReceiptScreen

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier, navHostController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = "main",
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { fadeOut() }
    ) {
        navigation(route = "main", startDestination = "food") {
            composable("profile") { ProfileScreen(navController = navHostController) }
            composable("edit") { EditProfileScreen(navController = navHostController) }
            composable("food") { FoodListScreen(navController = navHostController) }
            composable(
                route = "receipt/{receiptId}",
                arguments = listOf(navArgument("receiptId") { type = NavType.StringType })
            ) { backStackEntry ->
                backStackEntry.arguments?.getString("receiptId")?.let {
                    ReceiptScreen(recipeId = it)
                }
            }
        }
    }
}