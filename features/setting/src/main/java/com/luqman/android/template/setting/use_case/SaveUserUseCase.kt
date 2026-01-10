package com.luqman.android.template.setting.use_case

import com.luqman.android.template.repositories.user.UserRepository
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(
        name: String,
        title: String
    ) {
        userRepository.saveUser(name = name, title = title)
    }
}