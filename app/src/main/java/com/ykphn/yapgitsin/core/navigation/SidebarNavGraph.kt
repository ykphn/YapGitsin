package com.ykphn.yapgitsin.core.navigation


import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.ykphn.yapgitsin.presentation.foods.FoodListScreen
import com.ykphn.yapgitsin.presentation.recipe.ReceiptScreen


@Composable
fun SidebarNavGraph(
    modifier: Modifier = Modifier, navHostController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = "home",
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }

    ) {
        navigation(route = "home", startDestination = "food") {

            composable("food") {
                FoodListScreen(navController = navHostController)
            }

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