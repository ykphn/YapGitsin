package com.ykphn.yapgitsin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ykphn.yapgitsin.ui.theme.YapGitsinTheme
import com.ykphn.yapgitsin.views.root.YapGitsinRoot
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YapGitsinTheme {
                YapGitsinRoot()
            }
        }
    }
}