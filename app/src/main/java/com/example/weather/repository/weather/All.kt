package com.example.weather.repository.weather

class All {
    /* Volley
        /*private fun getResult(name: String) {
        val url = "https://api.weatherapi.com/v1/current.json?key=$API_KEY&q=$name&aqi=no:lang=ru"
        //val url = "https://api.weatherapi.com/v1/current.json?key=4af1e205156743f9af6231933222005&q=london"
        val queue = Volley.newRequestQueue(requireContext())
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                val obj = JSONObject(response)
                val temp = obj.getJSONObject("current")
                bindingWeatherInCity.description.text = temp.getString("temp_c")
                val temp1 = obj.getJSONObject("location")
                bindingWeatherInCity.desCity.text = "Город "+ temp1.getString("name") + "\n Страна " + temp1.getString("country")
            },
            {
                bindingWeatherInCity.description.text = "Город не найден"
                Log.d("MyLog", "Volley error: $it")
            }
        )
        queue.add(stringRequest)
    }

     */
    */
    /*
    Moshi
    * private fun getResultMoshi(name: String) {
        val url = "https://api.weatherapi.com/v1/current.json?key=4af1e205156743f9af6231933222005&q=$name&aqi=no:lang=ru"
        val queue = Volley.newRequestQueue(requireContext())
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                val moshi = Moshi.Builder().build()
                Log.e("Log", response.toString())
                val jsonAdapter: JsonAdapter<WeatherApiForm> = moshi.adapter(
                    WeatherApiForm::class.java)
                val city = jsonAdapter.fromJson(response)
                bindingWeatherInCity.desCity.text = "Город ${city?.location?.name} \n Страна ${city?.location?.country}"
                bindingWeatherInCity.description.text = city?.current?.temp_c.toString()
                Log.e("Log", "Имя ${city?.location?.country}")
            },
            {
                Log.d("MyLog", "Volley error: $it")
            }
        )
        queue.add(stringRequest)
    }
    * */

}