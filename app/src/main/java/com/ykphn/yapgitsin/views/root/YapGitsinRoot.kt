package com.ykphn.yapgitsin.views.root

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.ykphn.yapgitsin.core.navigation.SidebarNavGraph
import com.ykphn.yapgitsin.views.appbar.AppBar
import com.ykphn.yapgitsin.views.sidebar.Sidebar

@Composable
fun YapGitsinRoot(viewModel: YapGitsinViewModel = hiltViewModel()) {
    val isDrawerOpen by viewModel.isDrawerOpen.collectAsState()
    val navController = rememberNavController()

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    // ViewModel → UI
    LaunchedEffect(isDrawerOpen) {
        if (isDrawerOpen && !drawerState.isOpen) {
            drawerState.open()
        } else if (!isDrawerOpen && drawerState.isOpen) {
            drawerState.close()
        }
    }

    // UI → ViewModel
    LaunchedEffect(drawerState.isOpen) {
        if (drawerState.isOpen && !isDrawerOpen) {
            viewModel.openDrawer()
        } else if (!drawerState.isOpen && isDrawerOpen) {
            viewModel.closeDrawer()
        }
    }

    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
        Sidebar(onNavigate = { route ->
            navController.navigate(route)
            viewModel.closeDrawer()
        })
    }, content = {
        Scaffold(
            topBar = {
                AppBar(
                    onNavigationIconClick = {
                        viewModel.openDrawer()
                    })
            }) { innerPadding ->
            SidebarNavGraph(
                modifier = Modifier.padding(innerPadding), navHostController = navController
            )
        }
    })
}