package com.ykphn.yapgitsin.views.sidebar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ykphn.yapgitsin.data.model.SidebarItem

@Composable
fun SidebarHeader() {
    Box(
        modifier = Modifier.padding(32.dp)
    ) {
        Text(text = "Header", fontSize = 32.sp, color = MaterialTheme.colorScheme.onSurface)
    }
}

@Composable
fun SidebarBody(
    modifier: Modifier = Modifier, items: List<SidebarItem>, onItemClick: (SidebarItem) -> Unit
) {
    LazyColumn(modifier) {
        items(items) { item ->
            Row(modifier = Modifier
                .clickable {
                    onItemClick(item)
                }
                .padding(16.dp)) {
                Icon(
                    imageVector = item.icon, contentDescription = item.contentDescription, tint = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    modifier = Modifier.weight(1f), text = item.title, color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}