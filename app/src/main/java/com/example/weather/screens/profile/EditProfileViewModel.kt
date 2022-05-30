package com.example.weather.screens.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.repository.account.room.entities.Account
import com.example.weather.repository.account.AccountRepository
import com.example.weather.repository.account.room.entities.Field
import com.example.weather.repository.Repositories.accountsRepository
import com.example.weather.utils.*
import kotlinx.coroutines.launch

class EditProfileViewModel(
    private val accountsRepository: AccountRepository
) : ViewModel() {

    private val _currentAccount = MutableLiveData<Account>()
    val currentAccount = _currentAccount.share()

    private val _state = MutableLiveData(State())
    val state = _state.share()

    private val _navigate = MutableUnitLiveEvent()
    val navigate = _navigate.share()

    private val _clearFields = MutableUnitLiveEvent()
    val clearFields = _clearFields.share()

    init {
        viewModelScope.launch {
            accountsRepository.getAccount().collect{
                _currentAccount.value = it
            }
        }
    }


    fun onSaveButtonPressedVM(name: String) =
        viewModelScope.launch {
            showProgress()
            try {
                accountsRepository.changeName(name)
                processIsOK()
            } catch (e: EmptyFieldsExceptions) {
                emptyFieldsRuntimeException(e)
            }
            finally {
                hideProgress()
            }
        }


    private fun showProgress() {
        _state.value = _state.requireValue().copy(
            InProgress = true
        )
    }

    private fun emptyFieldsRuntimeException(e: EmptyFieldsExceptions) {
        _state.value = _state.requireValue().copy(
            InProgress = false,
            name = e.field == Field.Username
        )
    }

    private fun processIsOK() = _navigate.publishEvent()

    private fun hideProgress() {
        _state.value = _state.requireValue().copy(InProgress = false)
    }

    data class State(
        val InProgress: Boolean = false,
        val name: Boolean = false
    )
}