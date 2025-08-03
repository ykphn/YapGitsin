package com.ykphn.yapgitsin.presentation.auth.login.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SocialLoginRow(
    modifier: Modifier = Modifier,
    onAppleClick: () -> Unit,
    onFacebookClick: () -> Unit,
    onGmailClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        OutlinedButton(
            onClick = onAppleClick, modifier = Modifier.padding(horizontal = 4.dp)
        ) {
            Text("Apple")
        }
        OutlinedButton(
            onClick = onFacebookClick, modifier = Modifier.padding(horizontal = 4.dp)
        ) {
            Text("Facebook")
        }
        OutlinedButton(
            onClick = onGmailClick, modifier = Modifier.padding(horizontal = 4.dp)
        ) {
            Text("Gmail")
        }
    }
}