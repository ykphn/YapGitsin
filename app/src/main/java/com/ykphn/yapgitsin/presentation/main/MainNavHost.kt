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
import com.ykphn.yapgitsin.presentation.main.foods.FoodListScreen
import com.ykphn.yapgitsin.presentation.main.editprofile.EditProfileScreen
import com.ykphn.yapgitsin.presentation.main.profile.ProfileScreen
import com.ykphn.yapgitsin.presentation.main.recipe.ReceiptScreen

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
                val receiptId = backStackEntry.arguments?.getString("receiptId")?.toIntOrNull()
                if (receiptId != null) {
                    ReceiptScreen(receiptId = receiptId)
                }
            }
        }
    }
}