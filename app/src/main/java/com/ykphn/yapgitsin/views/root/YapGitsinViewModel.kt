package com.ykphn.yapgitsin.views.root

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class YapGitsinViewModel @Inject constructor() : ViewModel() {
    private val _isDrawerOpen = MutableStateFlow(false)
    val isDrawerOpen: StateFlow<Boolean> = _isDrawerOpen

    fun openDrawer() = _isDrawerOpen.tryEmit(true)
    fun closeDrawer() = _isDrawerOpen.tryEmit(false)
//    fun toggleDrawer() = _isDrawerOpen.update { !it }
}
