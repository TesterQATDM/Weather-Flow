package com.example.weather.screens.cityList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.repository.city.CityRepository
import com.example.weather.repository.city.room.entities.City
import com.example.weather.utils.requireValue
import com.example.weather.utils.share
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class CityListViewModel (
    private val cityRepository: CityRepository
): ViewModel() {

    private val _cities = MutableLiveData<List<City>>()
    val cities = _cities.share()

    private val _state = MutableLiveData(State())
    val state = _state.share()

    init{
        viewModelScope.launch {
            _cities.value = cityRepository.getAvailableCity()
        }
        listenerCurrentListCitiesVM()// listener events

    }

    private fun listenerCurrentListCitiesVM(): Job = viewModelScope.launch {
        cityRepository.listenerCurrentListCities()
            .collect {
                _cities.value = it
            }
    }

    fun deleteCity(city: City) = viewModelScope.launch {
        showProgress()
        cityRepository.deleteCity(city).collect { process ->
            _state.value = _state.requireValue().copy(
                InProgress = process
            )
        }
        processIsOK()
    }

    fun move(city: City, moveBy: Int): Job = viewModelScope.launch {
        showProgress()
        cityRepository.moveCity(city, moveBy)
        processIsOK()
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

    fun cancel() {
        viewModelScope.coroutineContext.cancelChildren()
        listenerCurrentListCitiesVM()
        processIsOK()
    }

    data class State(
        var InProgress: Int = 0
    )
}