package com.ykphn.yapgitsin.presentation.main.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ykphn.yapgitsin.R
import com.ykphn.yapgitsin.core.model.UiState
import com.ykphn.yapgitsin.presentation.common.screens.EmptyScreen
import com.ykphn.yapgitsin.presentation.common.screens.ErrorScreen
import com.ykphn.yapgitsin.presentation.common.screens.LoadingScreen
import com.ykphn.yapgitsin.presentation.main.viewmodels.ProfileViewModel
import com.ykphn.yapgitsin.presentation.main.models.UserProfile


@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewmodel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewmodel.uiState.collectAsState()
    val avatar by viewmodel.userAvatar.collectAsState()
    val userData by viewmodel.profileData.collectAsState()

    when (uiState) {
        UiState.Idle -> EmptyScreen()
        UiState.Loading -> LoadingScreen()
        UiState.Success -> ProfileSuccessScreen(
            modifier = modifier,
            userAvatar = avatar,
            userData = userData!!,
            buttonClicked = { navController.navigate("edit") }
        )

        UiState.Error -> ErrorScreen()
    }
}

@Composable
fun ProfileSuccessScreen(
    modifier: Modifier = Modifier,
    userAvatar: ImageBitmap?,
    userData: UserProfile,
    buttonClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        ProfileIncompleteCard(status = userData.fullName)
        ProfileSection(userAvatar, userData)
        Text(
            text = userData.bio ?: "Seni tanımak istiyoruz, ismini gir lütfen!",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(12.dp))
        EditProfileButton(onClick = buttonClicked)

    }
}

@Composable
fun ProfileIncompleteCard(
    modifier: Modifier = Modifier,
    status: String?,
) {
    status ?: run {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.error),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Text(
                text = "Bu profil tam olarak oluşturulmamış. Aşağıdaki butondan profilinizi tamamlayabilirsiniz.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onError,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ProfileSection(avatar: ImageBitmap?, userProfile: UserProfile) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .aspectRatio(1f)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
        ) {
            avatar?.let {
                Image(
                    bitmap = avatar,
                    contentDescription = "User Avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } ?: Image(
                painter = painterResource(id = R.drawable.profile_avatar),
                contentDescription = "Default Avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = userProfile.fullName ?: "Adın mı kayıp?",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.End
            )
            Text(
                text = "@${userProfile.username}",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                ProfileStat(title = "Beğeni", value = userProfile.likes ?: 0)
                ProfileStat(title = "Yıldız", value = userProfile.stars ?: 0)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Katıldı: ${userProfile.joinedDate}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ProfileStat(title: String, value: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value.toString(),
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun EditProfileButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = "Profili Düzenle",
            letterSpacing = 3.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}
