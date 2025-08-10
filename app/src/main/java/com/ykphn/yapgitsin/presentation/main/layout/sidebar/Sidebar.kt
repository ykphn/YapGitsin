package com.ykphn.yapgitsin.presentation.main.layout.sidebar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.ykphn.yapgitsin.presentation.main.layout.sidebar.model.SidebarItem
import com.ykphn.yapgitsin.presentation.main.layout.sidebar.components.SidebarBody
import com.ykphn.yapgitsin.presentation.main.layout.sidebar.components.SidebarHeader
import com.ykphn.yapgitsin.presentation.main.layout.sidebar.components.SidebarLogout

@Composable
fun Sidebar(
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.statusBars)
            .fillMaxHeight()
            .fillMaxWidth(0.6f)
            .clip(RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp))
            .background(color = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        SidebarHeader()
        SidebarBody(
            modifier = Modifier.weight(1f),
            items = listOf(
                SidebarItem(
                    id = "profile",
                    title = "Profile",
                    contentDescription = "Profile",
                    icon = Icons.Default.AccountCircle
                ), SidebarItem(
                    id = "food",
                    title = "Food",
                    contentDescription = "Food",
                    icon = Icons.Default.ShoppingCart
                ), SidebarItem(
                    id = "receipt/1",
                    title = "Receipt",
                    contentDescription = "Receipt",
                    icon = Icons.Default.DateRange
                )
            ), onItemClick = { item ->
                onNavigate(item.id)
            }
        )
        SidebarLogout(onLogoutClick = onLogout)
    }
}
