package com.ykphn.yapgitsin.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.ykphn.yapgitsin.core.domain.repository.AuthRepository
import com.ykphn.yapgitsin.presentation.auth.AuthActivity
import com.ykphn.yapgitsin.presentation.main.components.AppBar
import com.ykphn.yapgitsin.presentation.main.components.Sidebar
import com.ykphn.yapgitsin.ui.theme.YapGitsinTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var authRepository: AuthRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YapGitsinTheme {
                val navController = rememberNavController()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        Sidebar(
                            onNavigate = { route ->
                                navController.navigate(route)
                                scope.launch { drawerState.close() }
                            },
                            onLogout = {
                                scope.launch { drawerState.close() }
                                lifecycleScope.launch {
                                    authRepository.logout()
                                    startActivity(
                                        Intent(
                                            this@MainActivity,
                                            AuthActivity::class.java
                                        )
                                    )
                                    finish()
                                }
                            }
                        )
                    }
                ) {
                    Scaffold(
                        topBar = {
                            AppBar(
                                onNavigationIconClick = {
                                    scope.launch { drawerState.open() }
                                }
                            )
                        }
                    ) { innerPadding ->
                        MainNavHost(
                            modifier = Modifier.padding(innerPadding),
                            navHostController = navController
                        )
                    }
                }
            }
        }
    }
}
