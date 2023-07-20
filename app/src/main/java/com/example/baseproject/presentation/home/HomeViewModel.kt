package com.example.baseproject.presentation.home

import androidx.lifecycle.viewModelScope
import com.example.baseproject.base.BaseViewModel
import com.example.baseproject.data.model.User
import com.example.baseproject.domain.MainUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mainUseCase: MainUseCase
) : BaseViewModel() {

    private val _listUser: MutableStateFlow<List<User>> = MutableStateFlow(emptyList())
    val listUser: StateFlow<List<User>> = _listUser

    fun getListUser() {
        viewModelScope.launch {
            _listUser.value = mainUseCase.getListUser()
        }
    }
}