package com.ykphn.yapgitsin.presentation.main.editprofile

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ykphn.yapgitsin.presentation.common.screen.EmptyScreen
import com.ykphn.yapgitsin.presentation.common.screen.LoadingScreen
import com.ykphn.yapgitsin.presentation.main.editprofile.screen.SetupScreen
import com.ykphn.yapgitsin.presentation.main.editprofile.state.EditProfileUiState

@Composable
fun EditProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: EditProfileViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        EditProfileUiState.Error -> {
            Toast.makeText(
                context,
                "Bir hata oluştu. Daha sonra tekrar deneyiniz.",
                Toast.LENGTH_LONG
            ).show()
            navController.popBackStack()
        }

        EditProfileUiState.Idle -> SetupScreen(
            modifier,
            onProfileUpdate = { uri, name, username, bio ->
                viewModel.updateProfile(context, uri, name, username, bio)
            })

        EditProfileUiState.Loading -> {
            EmptyScreen(modifier)
            LoadingScreen(modifier)
        }

        EditProfileUiState.Success -> {
            Toast.makeText(context, "Profil başarıyla güncellendi", Toast.LENGTH_LONG).show()
            navController.popBackStack()
        }
    }


}
