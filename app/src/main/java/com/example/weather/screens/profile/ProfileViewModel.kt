package com.example.weather.screens.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.repository.account.room.entities.Account
import com.example.weather.repository.account.AccountRepository
import com.example.weather.utils.MutableUnitLiveEvent
import com.example.weather.utils.publishEvent
import com.example.weather.utils.share
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val accountsRepository: AccountRepository
) : ViewModel() {

    private val _currentAccount = MutableLiveData<Account>()
    val currentAccount = _currentAccount.share()

    private val _navigateToScreenSignInFragment = MutableUnitLiveEvent()
    val navigateToScreenSignInFragment = _navigateToScreenSignInFragment.share()

    init {
        viewModelScope.launch {
            accountsRepository.getAccount().collect {
                _currentAccount.value = it
            }
        }
    }

    fun logOutVM(){
        navigateToScreenSignIn()
    }

    private fun navigateToScreenSignIn() = _navigateToScreenSignInFragment.publishEvent()
}