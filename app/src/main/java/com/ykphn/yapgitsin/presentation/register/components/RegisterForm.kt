package com.ykphn.yapgitsin.presentation.register.components

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
import androidx.compose.runtime.derivedStateOf
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
import com.ykphn.yapgitsin.presentation.register.state.RegisterFormState

@Composable
fun RegisterForm(
    onRegisterClick: (String, String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val registerState = remember { mutableStateOf<RegisterFormState>(RegisterFormState.Idle) }

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var passwordRepeat by remember { mutableStateOf("") }
    var passwordRepeatVisible by remember { mutableStateOf(false) }

    val passwordMatch by remember { derivedStateOf { password == passwordRepeat } }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Kullanıcı Adı") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
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
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = passwordRepeat,
            onValueChange = { passwordRepeat = it },
            label = { Text("Şifre Tekrar") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (passwordRepeatVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordRepeatVisible = !passwordRepeatVisible }) {
                    Image(
                        painter = painterResource(id = if (passwordRepeatVisible) R.drawable.eye_open else R.drawable.eye_close),
                        contentDescription = if (passwordRepeatVisible) "Şifreyi Gizle" else "Şifreyi Göster"
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = when (registerState.value) {
                RegisterFormState.Idle -> ""
                RegisterFormState.UsernameError -> "Kullanıcı adı boş bırakılamaz!"
                RegisterFormState.EmailError -> "Mail adresi boş bırakılamaz!"
                RegisterFormState.PasswordError -> "Şifre alanı boş bırakılamaz!"
                RegisterFormState.PasswordMatchError -> "Şifreler uyuşmuyor!"
                RegisterFormState.Success -> ""
            },
            color = Color.Red,
            fontSize = 12.sp,
            letterSpacing = 1.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {
                if (username.isEmpty()) {
                    registerState.value = RegisterFormState.UsernameError
                } else if (email.isEmpty()) {
                    registerState.value = RegisterFormState.EmailError
                } else if (password.isEmpty()) {
                    registerState.value = RegisterFormState.PasswordError
                } else if (!passwordMatch) {
                    registerState.value = RegisterFormState.PasswordMatchError
                } else {
                    registerState.value = RegisterFormState.Success
                }

                if (registerState.value == RegisterFormState.Success) onRegisterClick(username, email, password)

            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Kayıt Ol", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}
