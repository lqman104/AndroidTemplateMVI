package com.luqman.android.template.dashboard.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.luqman.android.template.core.ui.theme.AndroidMVITemplateTheme
import com.luqman.android.template.dashboard.view_model.DashboardEvent
import com.luqman.android.template.dashboard.view_model.DashboardState

@Composable
fun DashboardScreen(
    modifier: Modifier,
    state: DashboardState,
    onEvent: (DashboardEvent) -> Unit = {},
    goToSetting: () -> Unit = {}
) {
    Scaffold(modifier) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = state.notes,
                onValueChange = {
                    onEvent(DashboardEvent.OnNoteChange(it))
                }
            )
            Text(state.notes)
            Button(
                onClick = goToSetting
            ) {
                Text("Navigate to setting")
            }
        }
    }
}

@Preview
@Composable
private fun DashboardScreenPrev() {
    AndroidMVITemplateTheme {
        DashboardScreen(
            modifier = Modifier.fillMaxSize(),
            state = DashboardState()
        )
    }
}