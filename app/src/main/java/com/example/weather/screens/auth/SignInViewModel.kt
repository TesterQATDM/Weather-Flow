package com.example.weather.screens.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.repository.account.AccountRepository
import com.example.weather.repository.account.room.entities.Field
import com.example.weather.utils.*
import kotlinx.coroutines.launch

class SignInViewModel(
    private val accountRepository: AccountRepository
): ViewModel() {

    private val _state = MutableLiveData(State())
    val state = _state.share()

    private val _navigateToTabFragment = MutableUnitLiveEvent()
    val navigateToTabFragment = _navigateToTabFragment.share()

    private val _clearFields = MutableUnitLiveEvent()
    val clearFields = _clearFields.share()

    fun signInAccount(email: String, password: String) =
        viewModelScope.launch {
        showProgress()
            try {
                accountRepository.signIn(email, password)
                processIsOK()
            } catch (e: EmptyFieldsExceptions) {
                emptyFieldsRuntimeException(e)
            }
            catch (e: AuthException) {
                authExceptionRuntimeException()
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
            emailEmpty = e.field == Field.Email,
            passwordEmpty = e.field == Field.Password
        )
    }

    private fun authExceptionRuntimeException(){
        _state.value = _state.requireValue().copy(
            InProgress = false,
        )
        clearFields()
    }

    private fun clearFields() = _clearFields.publishEvent()

    private fun processIsOK() = _navigateToTabFragment.publishEvent()

    private fun hideProgress() {
        _state.value = _state.requireValue().copy(InProgress = false)
    }

    data class State(
        val InProgress: Boolean = false,
        val emailEmpty: Boolean = false,
        val passwordEmpty: Boolean = false
    )
}