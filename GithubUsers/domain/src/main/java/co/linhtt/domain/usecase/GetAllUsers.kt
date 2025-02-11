package co.linhtt.domain.usecase

import co.linhtt.domain.repository.UserRepository
import javax.inject.Inject

class GetAllUsers @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke()= userRepository.getAllUsers()
}