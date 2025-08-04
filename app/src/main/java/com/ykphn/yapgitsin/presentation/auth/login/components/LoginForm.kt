package com.ykphn.yapgitsin.presentation.auth.login.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ykphn.yapgitsin.R
import com.ykphn.yapgitsin.presentation.auth.login.state.LoginFormState

@Composable
fun LoginForm(
    onLoginClick: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {

    val loginState = remember { mutableStateOf<LoginFormState>(LoginFormState.Idle) }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Şifre") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Image(
                        painter = painterResource(id = if (passwordVisible) R.drawable.eye_open else R.drawable.eye_close),
                        contentDescription = if (passwordVisible) "Şifreyi Gizle" else "Şifreyi Göster"
                    )
                }
            })
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = when (loginState.value) {
                LoginFormState.Idle -> ""
                LoginFormState.EmailError -> "Mail adresi boş bırakılamaz!"
                LoginFormState.PasswordError -> "Şifre alanı boş bırakılamaz!"
                LoginFormState.Success -> ""
            },
            color = Color.Red,
            fontSize = 12.sp,
            letterSpacing = 1.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {
                if (email.isEmpty()) loginState.value = LoginFormState.EmailError
                else if (password.isEmpty()) loginState.value = LoginFormState.PasswordError
                else loginState.value = LoginFormState.Success

                if (loginState.value == LoginFormState.Success)
                    onLoginClick(email, password)

            }, modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Giriş Yap", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}
