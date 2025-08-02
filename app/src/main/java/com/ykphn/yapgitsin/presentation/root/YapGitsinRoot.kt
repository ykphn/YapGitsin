package com.ykphn.yapgitsin.presentation.root

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ykphn.yapgitsin.core.navigation.SidebarNavGraph
import com.ykphn.yapgitsin.presentation.layouts.AppBar
import com.ykphn.yapgitsin.presentation.layouts.sidebar.Sidebar

@Composable
fun YapGitsinRoot(viewModel: YapGitsinViewModel = hiltViewModel()) {
    val isDrawerOpen by viewModel.isDrawerOpen.collectAsState()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showAppBar = currentRoute != "login" && currentRoute != "register"
    val drawerState = remember(currentRoute) { DrawerState(DrawerValue.Closed) }

    // ViewModel → UI
    LaunchedEffect(isDrawerOpen) {
        if (isDrawerOpen && !drawerState.isOpen) drawerState.open()
        else if (!isDrawerOpen && drawerState.isOpen) drawerState.close()
    }

    // UI → ViewModel
    LaunchedEffect(drawerState.isOpen) {
        if (drawerState.isOpen && !isDrawerOpen) viewModel.openDrawer()
        else if (!drawerState.isOpen && isDrawerOpen) viewModel.closeDrawer()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            if (showAppBar) {
                Sidebar(onNavigate = { route ->
                    navController.navigate(route)
                    viewModel.closeDrawer()
                })
            }
        },
        content = {
            Scaffold(
                topBar = {
                    if (showAppBar) AppBar(onNavigationIconClick = { viewModel.openDrawer() })
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
