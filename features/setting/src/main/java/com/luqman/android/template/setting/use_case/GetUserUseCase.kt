package com.luqman.android.template.setting.use_case

import com.luqman.android.template.repositories.models.User
import com.luqman.android.template.repositories.user.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke(): Flow<User> {
        return userRepository.getUser()
    }
}