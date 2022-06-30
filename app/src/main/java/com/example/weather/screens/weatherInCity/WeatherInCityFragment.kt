package com.example.weather.screens.weatherInCity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.weather.R
import com.example.weather.databinding.FragmentWeatherInCityBinding
import com.example.weather.moshiAndRetrofit.const.Const.BASE_URL_WITH_KEY
import com.example.weather.moshiAndRetrofit.entities.WeatherApiForm
import com.example.weather.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WeatherInCityFragment : BaseFragment(R.layout.fragment_weather_in_city){

    private lateinit var bindingWeatherInCity: FragmentWeatherInCityBinding
    override val viewModel by viewModels<WeatherInCityViewModel>()
    private val args by navArgs<WeatherInCityFragmentArgs>()

    private lateinit var city: WeatherApiForm

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingWeatherInCity = FragmentWeatherInCityBinding.bind(view)

        val url = if (args.city.mLatitudeTextView == 0.0){
            args.city.name
        } else{
            BASE_URL_WITH_KEY + "${args.city.mLatitudeTextView},${args.city.mLongitudeTextView}"

        }
        viewModel.loadCity(url)
        viewModel.weatherFromRetrofit.observe(viewLifecycleOwner){
            city = it
            bindingWeatherInCity.country.text = city.location.country
            bindingWeatherInCity.city.text = city.location.name
            bindingWeatherInCity.temp.text = city.current.temp_c.toString()
        }
    }
}
