package com.ykphn.yapgitsin.presentation.auth.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ykphn.yapgitsin.R
import com.ykphn.yapgitsin.core.model.UiState
import com.ykphn.yapgitsin.presentation.auth.viewmodels.ForgotPasswordViewModel
import com.ykphn.yapgitsin.presentation.common.screens.LoadingScreen

@Composable
fun ForgotPasswordScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {

    val viewModel: ForgotPasswordViewModel = hiltViewModel()
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        UiState.Error -> {
            Toast.makeText(
                context,
                context.getString(R.string.reset_password_error),
                Toast.LENGTH_SHORT
            ).show()
            viewModel.resetPage()
        }

        UiState.Idle -> {
            Box(modifier = modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ForgotPasswordHeader()

                    ForgotPasswordForm(
                        error = viewModel.errorMessage,
                        email = viewModel.email,
                        onEmailChange = { viewModel.updateEmail(it) },
                        onResetClick = { viewModel.forgotPassword() }
                    )

                    BackToLoginRow { navController.navigate("login") }
                }
            }
        }

        UiState.Loading -> LoadingScreen()
        UiState.Success -> {
            Toast.makeText(
                context,
                context.getString(R.string.reset_password_success),
                Toast.LENGTH_SHORT
            ).show()
            navController.navigate("login")
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
    error: Boolean,
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

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = if (error) stringResource(R.string.reset_password_error)
            else stringResource(R.string.empty_string),
            color = MaterialTheme.colorScheme.error,
            fontSize = 12.sp,
            letterSpacing = 1.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))

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
            color = MaterialTheme.colorScheme.primary,
            textDecoration = TextDecoration.Underline
        )
    }
}