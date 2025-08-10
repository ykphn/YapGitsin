package com.ykphn.yapgitsin.presentation.main.editprofile.screen

import android.net.Uri
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.ykphn.yapgitsin.presentation.main.editprofile.state.SetupScreenUiState

@Composable
fun SetupScreen(
    modifier: Modifier = Modifier,
    onProfileUpdate: (Uri, String, String, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }

    var uiState by remember { mutableStateOf<SetupScreenUiState>(SetupScreenUiState.Idle) }

    var photoUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        photoUri = uri
    }

    val hintText = when (uiState) {
        SetupScreenUiState.Idle -> "Lütfen tüm kutuları doldurun"
        SetupScreenUiState.AvatarError -> "Lütfen bir resim seçiniz"
        SetupScreenUiState.NameSurnameError -> "İsim ve soyisim alanı boş bırakılamaz"
        SetupScreenUiState.NicknameError -> "Kullanıcı adı boş bırakılamaz"
        SetupScreenUiState.BiographyError -> "Biyografi alanı boş bırakılamaz"
        SetupScreenUiState.Success -> ""
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = hintText,
            color = if (uiState == SetupScreenUiState.Idle) Color.Blue else Color.Red,
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
            onValueChange = { name = it },
            label = { Text("Ad Soyad") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Kullanıcı Adı") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = bio,
            onValueChange = { bio = it },
            label = { Text("Hakkında") },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            maxLines = 5
        )

        Button(
            onClick = {
                uiState = if (photoUri == null) SetupScreenUiState.AvatarError
                else if (name == "") SetupScreenUiState.NameSurnameError
                else if (username == "") SetupScreenUiState.NicknameError
                else if (bio == "") SetupScreenUiState.BiographyError
                else SetupScreenUiState.Success

                if (uiState == SetupScreenUiState.Success)
                    onProfileUpdate(photoUri!!, name, username, bio)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Kaydet")
        }
    }
}
