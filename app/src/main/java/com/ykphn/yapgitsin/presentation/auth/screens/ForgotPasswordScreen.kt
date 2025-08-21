package com.ykphn.yapgitsin.presentation.auth.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ykphn.yapgitsin.R

@Composable
fun ForgotPasswordScreen(
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ForgotPasswordHeader()

            ForgotPasswordForm(email = email, onEmailChange = { email = it }, onResetClick = {})

            BackToLoginRow { }
        }

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun ForgotPasswordHeader(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.forgot_password),
            contentDescription = "Forgot Password Illustration",
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.forgot_password_title),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.forgot_password_description),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            fontStyle = FontStyle.Italic,
            lineHeight = 24.sp
        )
    }
}

@Composable
fun ForgotPasswordForm(
    email: String,
    onEmailChange: (String) -> Unit,
    onResetClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text(text = stringResource(R.string.email_label)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = onResetClick, modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                text = stringResource(R.string.send_reset_link),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun BackToLoginRow(
    modifier: Modifier = Modifier, onBackClick: () -> Unit
) {
    Row(modifier = modifier) {
        Text(text = stringResource(R.string.back_to_login_prefix), letterSpacing = 1.sp)
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = stringResource(R.string.back_to_login_clickable),
            letterSpacing = 1.sp,
            modifier = Modifier.clickable { onBackClick() },
            color = Color.Blue,
            textDecoration = TextDecoration.Underline
        )
    }
}

@Preview(
    device = "spec:width=1080px,height=2400px,dpi=440", showSystemUi = true, showBackground = true
)
@Composable
fun ForgotPasswordScreenPreview() {
    ForgotPasswordScreen()
}