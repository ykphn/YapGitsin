package com.ykphn.yapgitsin.presentation.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.ykphn.yapgitsin.presentation.main.MainActivity
import com.ykphn.yapgitsin.ui.theme.YapGitsinTheme
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity : ComponentActivity() {
    @Inject lateinit var supabaseClient: SupabaseClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch {
            supabaseClient.auth.awaitInitialization()
            val user = supabaseClient.auth.currentUserOrNull()
            user?.let {
                startActivity(Intent(this@AuthActivity, MainActivity::class.java))
                finish()
            }
        }
        setContent {
            YapGitsinTheme {
                val navController = rememberNavController()
                Scaffold { innerPadding ->
                    AuthNavGraph(
                        modifier = Modifier.padding(innerPadding),
                        navHostController = navController
                    )
                }
            }
        }
    }
}