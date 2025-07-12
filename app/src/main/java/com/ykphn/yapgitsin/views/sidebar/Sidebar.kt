package com.ykphn.yapgitsin.views.sidebar

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.ykphn.yapgitsin.data.SidebarItem

@Composable
fun Sidebar(navController: NavController, onItemClick: () -> Unit) {
    Column(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.statusBars)
            .fillMaxHeight()
            .fillMaxWidth(0.6f)
            .background(color = Color.White)
    ) {
        SidebarHeader()
        SidebarBody(
            items = listOf(
                SidebarItem(
                    id = "home",
                    title = "Home",
                    contentDescription = "Home",
                    icon = Icons.Default.Home
                ),
                SidebarItem(
                    id = "food",
                    title = "food",
                    contentDescription = "food",
                    icon = Icons.Default.ShoppingCart
                ),
                SidebarItem(
                    id = "receipt/1",
                    title = "receipt",
                    contentDescription = "receipt",
                    icon = Icons.Default.DateRange
                )
            ),
            onItemClick = {
                Log.d("Sidebar", "Clicked on ${it.title}")
                navController.navigate(it.id)
                onItemClick()
            }
        )
    }
}