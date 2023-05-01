package com.example.baseproject.data

import com.example.baseproject.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val mainService: MainService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {


}