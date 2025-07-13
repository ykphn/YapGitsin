package com.ykphn.yapgitsin.core.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation


@Composable
fun SidebarNavGraph(
    modifier: Modifier = Modifier, navHostController: NavHostController
) {
    NavHost(
        modifier = modifier, navController = navHostController, startDestination = "home"
    ) {
        navigation(route = "home", startDestination = "food") {

            composable("food") {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Red)
                ) {
                    Text(text = "Food Screen", color = Color.White)
                    Button(
                        onClick = { navHostController.navigate("receipt/0") }) {
                        Text(text = "Go to Receipt with ID 0")
                    }
                }
            }

            composable(
                route = "receipt/{receiptId}",
                arguments = listOf(navArgument("receiptId") { type = NavType.StringType })
            ) { backStackEntry ->
                val receiptId = backStackEntry.arguments?.getString("receiptId") ?: "Unknown"

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Blue)
                ) {
                    Text(text = "Receipt Screen", color = Color.White)
                    Text(text = "Receipt ID: $receiptId", color = Color.White)
                    Button(
                        onClick = { navHostController.navigate("food") }) {
                        Text(text = "Back to Food")
                    }
                }
            }
        }
    }
}