package com.example.baseproject.domain

import com.example.baseproject.data.MainRepository
import com.example.baseproject.data.model.User
import javax.inject.Inject

class MainUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend fun getListUser(): List<User> {
        return mainRepository.getListUser()
    }
}