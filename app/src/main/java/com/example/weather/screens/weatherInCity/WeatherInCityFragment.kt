package com.example.weather.screens.weatherInCity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weather.R
import com.example.weather.databinding.FragmentWeatherInCityBinding
import com.example.weather.repository.Repositories
import com.example.weather.repository.city.room.entities.City
import com.example.weather.utils.viewModelCreator
import com.google.gson.Gson
import org.json.JSONObject

class WeatherInCityFragment : Fragment(R.layout.fragment_weather_in_city){

    private lateinit var bindingWeatherInCity: FragmentWeatherInCityBinding
    private val viewModel by viewModelCreator{WeatherInCityViewModel(Repositories.cityRepository)}
    private val args by navArgs<WeatherInCityFragmentArgs>()

    private lateinit var city: City
    private lateinit var weather: Weather

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingWeatherInCity = FragmentWeatherInCityBinding.bind(view)

        if (savedInstanceState == null) {
            viewModel.loadCity(args.city)
        }

        viewModel.cityD.observe(viewLifecycleOwner) {
            city = it
            bindingWeatherInCity.desCity.text = city.description
            getResult(city.name)
/**
*this api is blocked for users from Russian
**/

            /*            CoroutineScope(Dispatchers.Main).launch {
                val result = withContext(Dispatchers.IO) {
                    return@withContext WeatherInMemory(city)
                }
                viewModel.loadWeather(result.mMineUserEntity)
            }
        }
        viewModel.weatherAPI.observe(viewLifecycleOwner){result ->
            weather = result
            bindingWeatherInCity.temp.text = round(weather.main["temp"]!!.toDouble() - 273.15).toString()
            bindingWeatherInCity.description.text = weather.weather[0]["description"]
        }

 */
        }
    }

    private fun getResult(name: String) {
        val url = "https://api.weatherapi.com/v1/current.json?key=$API_KEY&q=$name&aqi=no:lang=ru"
        val queue = Volley.newRequestQueue(requireContext())
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                val mMineUserEntity = Gson().fromJson(response, Weather::class.java)
                bindingWeatherInCity.description.text = response.toString()
            },
            {
                Log.d("MyLog", "Volley error: $it")
            }
        )
        queue.add(stringRequest)
    }

    companion object{
        /*from https://www.weatherapi.com/ */
        const val API_KEY = "4af1e205156743f9af6231933222005"}
}
