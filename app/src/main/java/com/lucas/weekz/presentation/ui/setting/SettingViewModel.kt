package com.lucas.weekz.presentation.ui.setting

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingViewModel : ViewModel() {

    private val _isDarkTheme = MutableStateFlow(false) // 초기 테마 상태 (예: Light)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    fun setTheme(isDark: Boolean) {
        _isDarkTheme.value = isDark
    }
}