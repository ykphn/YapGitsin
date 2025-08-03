package com.ykphn.yapgitsin.presentation.auth.login.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SignUpRow(
    modifier: Modifier = Modifier,
    onSignUpClick: () -> Unit
) {
    Row(modifier = modifier) {
        Text(text = "Hesabınız yok mu?", letterSpacing = 1.sp)
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = "Kayıt Ol!",
            letterSpacing = 1.sp,
            modifier = Modifier.clickable { onSignUpClick() },
            color = Color.Blue,
            textDecoration = TextDecoration.Underline
        )
    }
}
