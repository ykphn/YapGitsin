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
import androidx.compose.material3.OutlinedButton
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
import com.ykphn.yapgitsin.presentation.auth.viewmodels.LoginScreenViewModel
import com.ykphn.yapgitsin.presentation.auth.state.AuthState
import com.ykphn.yapgitsin.presentation.common.screens.LoadingOverlay
import com.ykphn.yapgitsin.presentation.common.screens.LoadingScreen
import com.ykphn.yapgitsin.presentation.main.MainActivity

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginScreenViewModel = hiltViewModel(),
    navController: NavController,
) {
    val context = LocalContext.current
    val signInState by viewModel.signInState.collectAsState()

    LaunchedEffect(signInState) {
        when (signInState) {
            is AuthState.Error -> {
                Toast.makeText(
                    context,
                    context.getString(R.string.login_error_invalid_credentials),
                    Toast.LENGTH_SHORT
                ).show()
            }

            is AuthState.Success -> {
                Toast.makeText(
                    context,
                    context.getString(R.string.login_success),
                    Toast.LENGTH_SHORT
                ).show()
                (context as? Activity)?.let { activity ->
                    context.startActivity(Intent(context, MainActivity::class.java))
                    activity.finish()
                }
            }

            else -> Unit
        }
    }


    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginHeader()

            LoginForm(
                email = viewModel.email,
                password = viewModel.password,
                error = viewModel.errorMessage,
                onEmailChange = viewModel::updateEmail,
                onPasswordChange = viewModel::updatePassword,
                onLoginClick = viewModel::onLoginClick,
            )

            SocialLoginRow(onAppleClick = { }, onFacebookClick = { }, onGmailClick = { })

            SignUpRow(onSignUpClick = { navController.navigate("register") })
        }

        if (signInState == AuthState.Loading) {
            LoadingOverlay(modifier)
            LoadingScreen(modifier)
        }
    }

}

@Composable
fun LoginHeader(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.login_avatar),
            contentDescription = stringResource(R.string.login_image_desc),
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.login_greeting),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.login_welcome_message),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            fontStyle = FontStyle.Italic,
            lineHeight = 24.sp
        )
    }
}

@Composable
fun LoginForm(
    modifier: Modifier = Modifier,
    email: String,
    password: String,
    error: String?,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { onEmailChange(it) },
            label = { Text(stringResource(R.string.email_label)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { onPasswordChange(it) },
            label = { Text(stringResource(R.string.password_label)) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Image(
                        painter = painterResource(
                            id =
                                if (passwordVisible) R.drawable.eye_open
                                else R.drawable.eye_close
                        ),
                        contentDescription =
                            if (passwordVisible) stringResource(R.string.hide_password)
                            else stringResource(R.string.show_password)
                    )
                }
            })
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = error ?: stringResource(R.string.empty_string),
            color = Color.Red,
            fontSize = 12.sp,
            letterSpacing = 1.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = { onLoginClick() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                text = stringResource(R.string.login_button),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

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
            Text(stringResource(R.string.social_apple))
        }
        OutlinedButton(
            onClick = onFacebookClick, modifier = Modifier.padding(horizontal = 4.dp)
        ) {
            Text(stringResource(R.string.social_facebook))
        }
        OutlinedButton(
            onClick = onGmailClick, modifier = Modifier.padding(horizontal = 4.dp)
        ) {
            Text(stringResource(R.string.social_gmail))
        }
    }
}

@Composable
fun SignUpRow(
    modifier: Modifier = Modifier,
    onSignUpClick: () -> Unit
) {
    Row(modifier = modifier) {
        Text(text = stringResource(R.string.signup_prefix), letterSpacing = 1.sp)
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = stringResource(R.string.signup_clickable),
            letterSpacing = 1.sp,
            modifier = Modifier.clickable { onSignUpClick() },
            color = Color.Blue,
            textDecoration = TextDecoration.Underline
        )
    }
}
