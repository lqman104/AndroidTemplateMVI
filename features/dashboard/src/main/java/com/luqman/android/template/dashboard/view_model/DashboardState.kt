package com.luqman.android.template.dashboard.view_model

import com.luqman.android.template.repositories.models.User

data class DashboardState(
    val users: List<User>? = null,
    val notes: String = "",
)

sealed interface DashboardEvent {
    data object GetData: DashboardEvent
    data class OnNoteChange(val value: String): DashboardEvent
}
