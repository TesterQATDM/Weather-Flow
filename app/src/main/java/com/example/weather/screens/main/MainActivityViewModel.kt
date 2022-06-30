package com.example.weather.screens.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.repository.account.RoomAccountsRepository
import com.example.weather.utils.share
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val accountsRepository: RoomAccountsRepository
)
    :ViewModel() {

    private val _username = MutableLiveData<String>()
    val username = _username.share()

    init {
        viewModelScope.launch {
            // listening for the current account and send the username to be displayed in the toolbar
            accountsRepository.getAccount().collect {
                if (it == null) {
                    _username.value = ""
                } else {
                    _username.value = "@${it.username}"
                }
            }
        }
    }
}