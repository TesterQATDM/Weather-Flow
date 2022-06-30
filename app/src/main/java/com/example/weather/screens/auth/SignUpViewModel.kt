package com.example.weather.screens.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.repository.account.RoomAccountsRepository
import com.example.weather.repository.account.room.entities.Field
import com.example.weather.repository.account.room.entities.SignUpData
import com.example.weather.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountRepository: RoomAccountsRepository
): ViewModel() {

    private val _state = MutableLiveData(State())
    val state = _state.share()

    private val _navigateToSignInFragment = MutableUnitLiveEvent()
    val navigateToSignInFragment = _navigateToSignInFragment.share()

    private val _clearFieldsPassword = MutableUnitLiveEvent()
    val clearFieldsPassword = _clearFieldsPassword.share()

    private val _clearFieldsEmail = MutableUnitLiveEvent()
    val clearFieldsEmail = _clearFieldsEmail.share()


    fun createAccountVM(signUpData: SignUpData) =
        viewModelScope.launch {
            showProgress()
            try {
                accountRepository.createAccountRepository(signUpData = signUpData)
                processIsOK()
            } catch (e: EmptyFieldsExceptions) {
                emptyFieldsRunTimeException(e)
            }
            catch (e: AuthException) {
                authUpExceptionRuntimeException()
            }
            catch (e: AccountIsExistException) {
                accountIsExistRuntimeException()
            }
            catch (e: PasswordIsMismatchExceptions) {
                authUpExceptionRuntimeException()
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

    private fun emptyFieldsRunTimeException(e: EmptyFieldsExceptions) {
        _state.value = _state.requireValue().copy(
            InProgress = false,
            emailEmpty = e.field == Field.Email,
            nameEmpty = e.field == Field.Username,
            passwordEmpty = e.field == Field.Password,
            repeatPasswordEmpty = e.field == Field.RepeatPassword
        )
    }

    private fun authUpExceptionRuntimeException(){
        _state.value = _state.requireValue().copy(
            InProgress = false,
        )
        clearFieldsPassword()
    }

    private fun accountIsExistRuntimeException(){
        _state.value = _state.requireValue().copy(
            InProgress = false,
        )
        clearFieldsEmail()
    }

    //if password_mismatch
    private fun clearFieldsPassword() = _clearFieldsPassword.publishEvent()

    //if account is already existing
    private fun clearFieldsEmail() = _clearFieldsEmail.publishEvent()

    private fun processIsOK() = _navigateToSignInFragment.publishEvent()

    private fun hideProgress() {
        _state.value = _state.requireValue().copy(InProgress = false)
    }

    data class State(
        val InProgress: Boolean = false,
        val emailEmpty: Boolean = false,
        val nameEmpty: Boolean = false,
        val passwordEmpty: Boolean = false,
        val repeatPasswordEmpty: Boolean = false
    )
}