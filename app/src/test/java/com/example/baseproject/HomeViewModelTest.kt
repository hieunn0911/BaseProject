package com.example.baseproject

import com.example.baseproject.data.model.User
import com.example.baseproject.domain.MainUseCase
import com.example.baseproject.presentation.home.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.mockito.kotlin.wheneverBlocking

class HomeViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getListUser() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        try {
            val useCase = mock<MainUseCase>()
            val viewModel = HomeViewModel(useCase)
            whenever(useCase.getListUser()).thenReturn(listOf(User(avatarUrl = "Hehe")))
            viewModel.getListUser()
            assert(viewModel.listUser.value.first().avatarUrl == "Hehe")
        } finally {
            Dispatchers.resetMain()
        }
    }
}