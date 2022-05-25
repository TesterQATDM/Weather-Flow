package com.example.weather.screens.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.navOptions
import com.example.weather.R
import com.example.weather.databinding.FragmentProfileBinding
import com.example.weather.repository.Repositories
import com.example.weather.utils.findTopNavController
import com.example.weather.utils.viewModelCreator
import kotlinx.android.synthetic.main.activity_main.*

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel by viewModelCreator { ProfileViewModel(Repositories.accountRepository) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        binding.editProfileButton.setOnClickListener {editProfileButtonFragment()}
        binding.logoutButton.setOnClickListener {logoutButtonFragment()}
        currentProfile()
        navigateToScreenSignInFragment()
    }

    private fun currentProfile() = viewModel.currentAccount.observe(viewLifecycleOwner){
        if (it == null) {
            return@observe
        }
        binding.emailTextView.text = it.email
        binding.usernameTextView.text = it.username
    }

    private fun navigateToScreenSignInFragment() = viewModel.navigateToScreenSignInFragment.observe(viewLifecycleOwner){
        findTopNavController().navigate(R.id.signInFragment, null, navOptions {
            popUpTo(R.id.tabsFragment) {
                inclusive = true
            }
        })
    }

    private fun editProfileButtonFragment(){
        findTopNavController().navigate(R.id.editProfileFragment)
    }

    private fun logoutButtonFragment(){
        requireActivity().usernameTextView.setText("")
        viewModel.logOutVM()
    }
}