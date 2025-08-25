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
import androidx.compose.foundation.layout.Row
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
import com.ykphn.yapgitsin.presentation.main.models.EditProfileStep
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
            step = viewModel.step,
            photoUri = viewModel.uri,
            name = viewModel.name,
            bio = viewModel.bio,
            error = viewModel.errorMessage,
            onPhotoUriChange = viewModel::updateUri,
            onNameChange = viewModel::updateName,
            onBioChange = viewModel::updateBio,
            onBackClick = viewModel::onBackClick,
            onUpdateAvatarClick = viewModel::updateProfileAvatar,
            onUpdateProfileClick = viewModel::updateProfileField
        )

        UiState.Loading -> {
            EmptyScreen(modifier)
            LoadingScreen(modifier)
        }

        UiState.Success -> {
            Toast.makeText(context, "Profil başarıyla güncellendi", Toast.LENGTH_LONG).show()
            navController.navigate("profile")
        }
    }

}

@Composable
fun SetupScreen(
    modifier: Modifier = Modifier,
    context: Context,
    step: EditProfileStep,
    photoUri: Uri?,
    name: String,
    bio: String,
    error: String?,
    onPhotoUriChange: (Uri) -> Unit,
    onNameChange: (String) -> Unit,
    onBioChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onUpdateAvatarClick: (Context) -> Unit,
    onUpdateProfileClick: () -> Unit
) {

    Column (
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(48.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = error ?: "Lütfen tüm kutuları doldurun",
                color = error?.let { Color.Red } ?: Color.Blue,
                textAlign = TextAlign.Center,
                letterSpacing = 1.sp,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        when (step) {
            EditProfileStep.PHOTO -> PhotoStep(
                photoUri = photoUri,
                onPhotoUriChange = onPhotoUriChange,
                onFinish = { onUpdateAvatarClick(context) }
            )

            EditProfileStep.INFO -> InfoStep(
                name = name,
                bio = bio,
                onNameChange = onNameChange,
                onBioChange = onBioChange,
                onBack = onBackClick,
                onFinish = { onUpdateProfileClick() }
            )
        }
    }
}

@Composable
fun PhotoStep(
    modifier: Modifier = Modifier,
    photoUri: Uri?,
    onPhotoUriChange: (Uri) -> Unit,
    onFinish: () -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { it?.let { onPhotoUriChange(it) } }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
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
                Text("Fotoğraf Ekle")
            }
        }

        Button(
            onClick = onFinish,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ) {
            Text("Devam Et")
        }
    }
}

@Composable
fun InfoStep(
    modifier: Modifier = Modifier,
    name: String,
    bio: String,
    onNameChange: (String) -> Unit,
    onBioChange: (String) -> Unit,
    onBack: () -> Unit,
    onFinish: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Ad Soyad") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = bio,
            onValueChange = onBioChange,
            label = { Text("Hakkında") },
            maxLines = 5,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = onBack, modifier = Modifier.weight(1f)) {
                Text("Geri")
            }
            Button(onClick = onFinish, modifier = Modifier.weight(1f)) {
                Text("Kaydet")
            }
        }
    }
}
