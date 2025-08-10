package com.ykphn.yapgitsin.presentation.main.profile.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ykphn.yapgitsin.presentation.main.profile.components.EditProfileButton
import com.ykphn.yapgitsin.presentation.main.profile.components.ProfileIncompleteCard
import com.ykphn.yapgitsin.presentation.main.profile.components.ProfileSection
import com.ykphn.yapgitsin.presentation.main.profile.model.UserProfile

@Composable
fun ProfileSuccessScreen(
    modifier: Modifier = Modifier,
    userData: UserProfile,
    buttonClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        ProfileIncompleteCard(status = userData.fullName)
        ProfileSection(userData)
        Text(
            text = userData.bio ?: "Seni tanımak istiyoruz, ismini gir lütfen!",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(12.dp))
        EditProfileButton(onClick = buttonClicked)

    }
}