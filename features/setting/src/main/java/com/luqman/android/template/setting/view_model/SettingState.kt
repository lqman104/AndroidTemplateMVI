package com.luqman.android.template.setting.view_model

data class SettingState(
    val name: String? = null,
    val title: String? = null
)

sealed interface SettingEvent {
    data object GetData : SettingEvent
    data class OnNameChange(val value: String) : SettingEvent
    data class OnTitleChange(val value: String) : SettingEvent
    data object OnSave : SettingEvent
}