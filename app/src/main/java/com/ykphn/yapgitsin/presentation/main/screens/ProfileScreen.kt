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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.ykphn.yapgitsin.R
import com.ykphn.yapgitsin.core.model.UiState
import com.ykphn.yapgitsin.presentation.common.screens.EmptyScreen
import com.ykphn.yapgitsin.presentation.common.screens.ErrorScreen
import com.ykphn.yapgitsin.presentation.common.screens.LoadingScreen
import com.ykphn.yapgitsin.presentation.main.models.Food
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
    val favorites = viewmodel.favorites

    when (uiState) {
        UiState.Idle -> EmptyScreen()
        UiState.Loading -> LoadingScreen()
        UiState.Success -> {
            Column(modifier = modifier.fillMaxSize()) {
                ProfileSuccessScreen(
                    userAvatar = avatar,
                    userData = userData!!,
                    favorites = favorites,
                    buttonClicked = { navController.navigate("edit") }
                )
            }
        }

        UiState.Error -> ErrorScreen()
    }
}

@Composable
fun ProfileSuccessScreen(
    modifier: Modifier = Modifier,
    userAvatar: ImageBitmap?,
    userData: UserProfile,
    favorites: List<Food>,
    buttonClicked: () -> Unit
) {
    Column(
        modifier = modifier
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
        FoodCardWithActions(
            title = "Favoriler",
            foods = favorites,
            onClick = {},
            onLikeClick = {},
            onStarClick = {}
        )
        FoodCardWithActions(
            title = "Yıldızlılar",
            foods = favorites,
            onClick = {},
            onLikeClick = {},
            onStarClick = {}
        )
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
                ProfileStat(title = "Beğeni", value = userProfile.likes.size)
                ProfileStat(title = "Yıldız", value = userProfile.stars.size)
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

@Composable
fun FoodCardWithActions(
    modifier: Modifier = Modifier,
    title: String,
    foods: List<Food>,
    onClick: (String) -> Unit,
    onLikeClick: (String) -> Unit,
    onStarClick: (String) -> Unit
) {
    Text(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth(),
        text = title,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        fontSize = 16.sp,
        letterSpacing = 2.sp,
        color = MaterialTheme.colorScheme.onBackground
    )
    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item { }
        items(foods) { food ->
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                elevation = CardDefaults.cardElevation(2.dp),
                shape = RoundedCornerShape(16.dp),
                onClick = { onClick(food.id) }
            ) {
                val request = ImageRequest.Builder(LocalContext.current)
                    .data(food.imageUrl)
                    .crossfade(true)
                    .build()

                AsyncImage(
                    model = request,
                    contentDescription = food.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(90.dp)
                        .align(Alignment.CenterHorizontally)
                        .clip(RoundedCornerShape(12.dp))
                )

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = food.name,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))

                // Alt kısım: Beğeni ve yıldız
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(onClick = { onLikeClick(food.id) }) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Beğen",
                            tint = Color.Red
                        )
                    }
                    IconButton(onClick = { onStarClick(food.id) }) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Yıldızla",
                            tint = Color.Yellow
                        )
                    }
                }
            }
        }
        item { }

    }
}
