package com.ykphn.yapgitsin.presentation.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ykphn.yapgitsin.presentation.main.models.SidebarItem

@Composable
fun Sidebar(
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier.Companion
            .windowInsetsPadding(WindowInsets.Companion.statusBars)
            .fillMaxHeight()
            .fillMaxWidth(0.6f)
            .clip(RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp))
            .background(color = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        SidebarHeader()
        SidebarBody(
            modifier = Modifier.Companion.weight(1f),
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

@Composable
fun SidebarBody(
    modifier: Modifier = Modifier, items: List<SidebarItem>, onItemClick: (SidebarItem) -> Unit
) {
    LazyColumn(modifier) {
        items(items) { item ->
            Row(
                modifier = Modifier.Companion
                    .clickable {
                        onItemClick(item)
                    }
                    .padding(16.dp)) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.contentDescription,
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.Companion.width(16.dp))
                Text(
                    modifier = Modifier.Companion.weight(1f),
                    text = item.title,
                    letterSpacing = 1.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
fun SidebarHeader(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.Companion.horizontalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        MaterialTheme.colorScheme.surface
                    )
                )
            )
            .padding(vertical = 32.dp, horizontal = 32.dp)
    ) {
        Column {
            Text(
                text = "Yap Gitsin",
                letterSpacing = 3.sp,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Companion.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.Companion.height(8.dp))
            Text(
                text = "Anneye sorma artık, bak gitsin",
                style = MaterialTheme.typography.labelMedium.copy(fontStyle = FontStyle.Companion.Italic),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
fun SidebarLogout(
    modifier: Modifier = Modifier,
    onLogoutClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.Companion.horizontalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        MaterialTheme.colorScheme.surfaceVariant
                    )
                )
            )
            .padding(vertical = 32.dp, horizontal = 32.dp)
            .clickable { onLogoutClick() }
    ) {
        Row(verticalAlignment = Alignment.Companion.CenterVertically) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                contentDescription = "Çıkış Yap",
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.Companion.width(12.dp))
            Text(
                text = "Çıkış Yap",
                letterSpacing = 1.sp,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Companion.Bold),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}