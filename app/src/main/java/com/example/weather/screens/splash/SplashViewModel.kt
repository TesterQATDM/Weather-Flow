package com.example.weather.screens.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.repository.account.AccountRepository
import com.example.weather.repository.account.RoomAccountsRepository
import com.example.weather.utils.share
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val accountRepository: AccountRepository
): ViewModel(){

    private val _launchMainScreen = MutableLiveData<Boolean>()
    val launchMainScreen = _launchMainScreen.share()

    init {
        viewModelScope.launch {
            _launchMainScreen.value = accountRepository.isSignedIn()
        }
    }

}