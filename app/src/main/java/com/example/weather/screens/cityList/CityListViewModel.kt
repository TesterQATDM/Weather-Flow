package com.example.weather.screens.cityList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.repository.city.CityRepository
import com.example.weather.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityListViewModel @Inject constructor(
    private val cityRepository: CityRepository
): ViewModel() {

    private val _citiesRoom = MutableLiveData<List<String>>()
    val citiesRoom = _citiesRoom.share()

    private val _state = MutableLiveData(State())
    val state = _state.share()

    private val _cityIsExist = MutableUnitLiveEvent()
    val cityIsExist = _cityIsExist.share()

    init{
        listenerCurrentListCitiesVM2()
        listenerCurrentListCitiesVM()
    }

    fun deleteCityRoom(string: String) = viewModelScope.launch {
        showProgress()
        cityRepository.deleteCityRoom(string)
        processIsOK()
    }

    fun addCity(cityName: String) = viewModelScope.launch{
        try {
            cityRepository.addCity(cityName)
        }
        catch (e: CityIsExistException){
            cityIsExistException()
        }
    }

    private fun showProgress() {
        _state.value =_state.requireValue().copy(
            InProgress = 0
        )
    }

    private fun processIsOK() {
        _state.value = _state.requireValue().copy(
            InProgress = 0
        )
    }

    private fun listenerCurrentListCitiesVM2(){
        viewModelScope.launch {
            _citiesRoom.value = cityRepository.getAvailableCityRoom()
        }
    }

    private fun listenerCurrentListCitiesVM(): Job = viewModelScope.launch {
        cityRepository.listenerCurrentListCities()
            .collect {
                _citiesRoom.value = it
            }
    }

    private fun cityIsExistException() = _cityIsExist.publishEvent()

    data class State(
        var InProgress: Int = 0
    )
}