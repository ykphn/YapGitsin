package com.ykphn.yapgitsin.presentation.auth.screens

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ykphn.yapgitsin.R
import com.ykphn.yapgitsin.presentation.auth.viewmodels.RegisterScreenViewModel
import com.ykphn.yapgitsin.presentation.auth.state.AuthState
import com.ykphn.yapgitsin.presentation.common.screens.LoadingOverlay
import com.ykphn.yapgitsin.presentation.common.screens.LoadingScreen
import com.ykphn.yapgitsin.presentation.main.MainActivity

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val signUpState by viewModel.signUpState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(signUpState) {
        when (val state = signUpState) {
            is AuthState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }

            is AuthState.Success -> {
                val activity = context as? Activity
                context.startActivity(Intent(context, MainActivity::class.java))
                activity?.finish()
            }

            else -> Unit
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RegisterHeader()

            RegisterForm(
                username = viewModel.username,
                email = viewModel.email,
                password = viewModel.password,
                passwordRepeat = viewModel.passwordRepeat,
                errorMessage = viewModel.errorMessage,
                setUserName = viewModel::updateUsername,
                setEmail = viewModel::updateEmail,
                setPassword = viewModel::updatePassword,
                setRepeatPassword = viewModel::updateRepeatPassword,
                onRegisterClick = { viewModel.onLoginClick(context) }
            )

            RegisterSignInRow(onSignInClick = { navController.navigate("login") })
        }

        if (signUpState == AuthState.Loading) {
            LoadingOverlay()
            LoadingScreen()
        }
    }
}

@Composable
fun RegisterHeader(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.register_avatar),
            contentDescription = stringResource(R.string.register_image_desc),
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.register_welcome_title),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.register_welcome_description),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            fontStyle = FontStyle.Italic,
            lineHeight = 24.sp
        )
    }
}

@Composable
fun RegisterForm(
    modifier: Modifier = Modifier,
    username: String,
    email: String,
    password: String,
    passwordRepeat: String,
    errorMessage: String?,
    setUserName: (String) -> Unit,
    setEmail: (String) -> Unit,
    setPassword: (String) -> Unit,
    setRepeatPassword: (String) -> Unit,
    onRegisterClick: () -> Unit,
) {

    var passwordVisible by remember { mutableStateOf(false) }
    var passwordRepeatVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = username,
            onValueChange = { setUserName(it) },
            label = { Text(text = stringResource(R.string.username_label)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { setEmail(it) },
            label = { Text(text = stringResource(R.string.email_label)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { setPassword(it) },
            label = { Text(text = stringResource(R.string.password_label)) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Image(
                        painter = painterResource(
                            id = if (passwordVisible) R.drawable.eye_open else R.drawable.eye_close
                        ),
                        contentDescription =
                            if (passwordVisible) stringResource(R.string.hide_password)
                            else stringResource(R.string.show_password)
                    )
                }
            })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = passwordRepeat,
            onValueChange = { setRepeatPassword(it) },
            label = { Text(text = stringResource(R.string.password_repeat_label)) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (passwordRepeatVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordRepeatVisible = !passwordRepeatVisible }) {
                    Image(
                        painter = painterResource(
                            id = if (passwordRepeatVisible) R.drawable.eye_open else R.drawable.eye_close
                        ),
                        contentDescription =
                            if (passwordRepeatVisible) stringResource(R.string.hide_password)
                            else stringResource(R.string.show_password)
                    )
                }
            })
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = errorMessage.orEmpty(),
            color = MaterialTheme.colorScheme.error,
            fontSize = 12.sp,
            letterSpacing = 1.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = { onRegisterClick() }, modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                text = stringResource(R.string.register_button),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun RegisterSignInRow(
    modifier: Modifier = Modifier, onSignInClick: () -> Unit
) {
    Row(modifier = modifier) {
        Text(text = stringResource(R.string.register_signin_prefix), letterSpacing = 1.sp)
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = stringResource(R.string.register_signin_clickable),
            letterSpacing = 1.sp,
            modifier = Modifier.clickable { onSignInClick() },
            color = MaterialTheme.colorScheme.primary,
            textDecoration = TextDecoration.Underline
        )
    }
}
