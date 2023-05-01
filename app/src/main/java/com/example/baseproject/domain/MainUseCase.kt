package com.example.baseproject.domain

import com.example.baseproject.data.MainRepository
import javax.inject.Inject

class MainUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {

}