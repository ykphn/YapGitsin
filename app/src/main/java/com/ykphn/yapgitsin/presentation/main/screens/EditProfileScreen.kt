package com.ykphn.yapgitsin.presentation.main.screens

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.ykphn.yapgitsin.core.model.UiState
import com.ykphn.yapgitsin.presentation.common.screens.EmptyScreen
import com.ykphn.yapgitsin.presentation.common.screens.LoadingScreen
import com.ykphn.yapgitsin.presentation.main.viewmodels.EditProfileViewModel

@Composable
fun EditProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: EditProfileViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        UiState.Error -> {
            Toast.makeText(
                context,
                "Bir hata oluştu. Daha sonra tekrar deneyiniz.",
                Toast.LENGTH_LONG
            ).show()
            navController.popBackStack()
        }

        UiState.Idle -> SetupScreen(
            modifier = modifier,
            context = context,
            photoUri = viewModel.uri,
            name = viewModel.name,
            username = viewModel.username,
            bio = viewModel.bio,
            error = viewModel.errorMessage,
            onPhotoUriChange = viewModel::updateUri,
            onNameChange = viewModel::updateName,
            onUsernameChange = viewModel::updateUsername,
            onBioChange = viewModel::updateBio,
            onUpdateClick = viewModel::onUpdateClick
        )

        UiState.Loading -> {
            EmptyScreen(modifier)
            LoadingScreen(modifier)
        }

        UiState.Success -> {
            Toast.makeText(context, "Profil başarıyla güncellendi", Toast.LENGTH_LONG).show()
            navController.popBackStack()
        }
    }

}

@Composable
fun SetupScreen(
    modifier: Modifier = Modifier,
    context: Context,
    photoUri: Uri?,
    name: String,
    username: String,
    bio: String,
    error: String?,
    onPhotoUriChange: (Uri) -> Unit,
    onNameChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onBioChange: (String) -> Unit,
    onUpdateClick: (Context) -> Unit,
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { it?.let { onPhotoUriChange(it) } }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = error ?: "Lütfen tüm kutuları doldurun",
            color = error?.let { Color.Red } ?: Color.Blue,
            textAlign = TextAlign.Center,
            letterSpacing = 1.sp,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        // Fotoğraf Yükleyici
        Box(
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1f)
                .clip(CircleShape)
                .background(Color.LightGray)
                .clickable { launcher.launch("image/*") },
            contentAlignment = Alignment.Center
        ) {
            if (photoUri != null) {
                AsyncImage(
                    model = photoUri,
                    contentDescription = "Profile Photo",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Text("Fotoğraf Ekle", textAlign = TextAlign.Center)
            }
        }

        OutlinedTextField(
            value = name,
            onValueChange = { onNameChange(it) },
            label = { Text("Ad Soyad") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = username,
            onValueChange = { onUsernameChange(it) },
            label = { Text("Kullanıcı Adı") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = bio,
            onValueChange = { onBioChange(it) },
            label = { Text("Hakkında") },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            maxLines = 5
        )

        Button(
            onClick = { onUpdateClick(context) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Kaydet")
        }
    }
}
