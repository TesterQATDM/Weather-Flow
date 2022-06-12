package com.example.weather.screens.splash

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.weather.R
import com.example.weather.databinding.FragmentSplashBinding
import com.example.weather.repository.Singletons
import com.example.weather.screens.main.MainActivity

import com.example.weather.screens.main.MainActivityArgs
import com.example.weather.utils.viewModelCreator

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private lateinit var binding: FragmentSplashBinding
    private val viewModel by viewModelCreator{SplashViewModel(Singletons.accountsRepository)}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSplashBinding.bind(view)

        renderAnimations()
        viewModel.launchMainScreen.observe(viewLifecycleOwner){
            launchMainActivity(it)
        }

    }

    private fun launchMainActivity(it: Boolean) {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        val args = MainActivityArgs(it)
        intent.putExtras(args.toBundle())
        startActivity(intent)
    }

    private fun renderAnimations() {
        binding.progressBar.alpha = 1f
        binding.progressBar.animate()
            .alpha(1f)
            .setDuration(5000)
            .start()

        binding.plzWait.alpha = 1f
        binding.plzWait.animate()
            .alpha(0f)
            .setStartDelay(500)
            .setDuration(5000)
            .start()
    }
}