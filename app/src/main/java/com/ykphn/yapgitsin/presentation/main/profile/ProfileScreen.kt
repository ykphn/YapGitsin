package com.ykphn.yapgitsin.presentation.main.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ykphn.yapgitsin.core.model.UiState
import com.ykphn.yapgitsin.presentation.common.screen.ErrorScreen
import com.ykphn.yapgitsin.presentation.common.screen.LoadingScreen
import com.ykphn.yapgitsin.presentation.main.profile.screen.ProfileSuccessScreen

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewmodel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewmodel.uiState.collectAsState()
    val userData by viewmodel.profileData.collectAsState()

    when (uiState) {
        UiState.Error -> ErrorScreen()
        UiState.Loading -> LoadingScreen()
        UiState.Success -> ProfileSuccessScreen(
            modifier = modifier,
            userData = userData!!,
            buttonClicked = { navController.navigate("edit") }
        )
    }
}
