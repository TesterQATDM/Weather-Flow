package com.example.weather.screens.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weather.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }
}