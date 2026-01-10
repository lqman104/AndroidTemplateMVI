package com.luqman.android.template.setting.view_model

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(): ViewModel() {

    private val _state: MutableStateFlow<SettingState> = MutableStateFlow(SettingState())
    val state = _state.asStateFlow()

    fun onEvent(event: SettingEvent) {
        when(event) {
            is SettingEvent.OnNameChange -> onNameChange(event.value)
            is SettingEvent.OnTitleChange -> onTitleChange(event.value)
        }
    }

    private fun onNameChange(value: String) {
        _state.value = _state.value.copy(name = value)
    }

    private fun onTitleChange(value: String) {
        _state.value = _state.value.copy(title = value)
    }
}