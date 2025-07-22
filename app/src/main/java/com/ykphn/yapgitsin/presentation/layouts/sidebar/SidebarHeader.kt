package com.ykphn.yapgitsin.presentation.layouts.sidebar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SidebarHeader() {
    Box(
        modifier = Modifier.padding(32.dp)
    ) {
        Text(text = "Header", fontSize = 32.sp, color = MaterialTheme.colorScheme.onSurface)
    }
}