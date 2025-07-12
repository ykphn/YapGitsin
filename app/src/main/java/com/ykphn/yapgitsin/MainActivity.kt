package com.ykphn.yapgitsin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.ykphn.yapgitsin.core.navigation.SidebarNavGraph
import com.ykphn.yapgitsin.ui.theme.YapGitsinTheme
import com.ykphn.yapgitsin.views.appbar.AppBar
import com.ykphn.yapgitsin.views.sidebar.Sidebar
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YapGitsinTheme {
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                val navController = rememberNavController()
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        Sidebar(navController, onItemClick = {
                            scope.launch {
                                drawerState.close()
                            }
                        })
                    },
                    content = {
                        Scaffold(
                            modifier = Modifier.fillMaxSize(),
                            topBar = {
                                AppBar(
                                    onNavigationIconClick = {
                                        scope.launch {
                                            drawerState.open()
                                        }
                                    }
                                )
                            }
                        ) { innerPadding ->
                            SidebarNavGraph(
                                modifier = Modifier.padding(innerPadding),
                                navHostController = navController
                            )
                        }
                    }
                )

            }
        }
    }
}