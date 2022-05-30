package com.example.weather.screens.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.repository.account.AccountRepository
import com.example.weather.repository.account.room.AccountsDao
import com.example.weather.utils.share
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val accountsRepository: AccountRepository)
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