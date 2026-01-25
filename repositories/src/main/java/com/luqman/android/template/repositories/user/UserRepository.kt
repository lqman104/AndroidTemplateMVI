package com.luqman.android.template.repositories.user

import android.util.Log
import androidx.datastore.core.DataStore
import com.luqman.android.template.mvi.datastore.UserPreferences
import com.luqman.android.template.mvi.datastore.copy
import com.luqman.android.template.repositories.service.user.UserApiService
import com.luqman.android.template.repositories.service.user.response.UserResponse
import javax.inject.Inject
import javax.inject.Singleton

interface UserRepository {
    suspend fun fetchUser(): Result<UserResponse>
    suspend fun saveUser(name: String, title: String)
}

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService,
    private val dataStore: DataStore<UserPreferences>
): UserRepository {

    override suspend fun fetchUser() = runCatching {
        val result = userApiService.getUsers()
        Log.d("result", "fetchUser: $result")
        return@runCatching result
    }

    override suspend fun saveUser(name: String, title: String) {
        dataStore.updateData { userPreferences ->
            userPreferences.copy {
                this.title = title
                this.name = name
            }
        }
    }
}