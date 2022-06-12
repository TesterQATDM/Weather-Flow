package com.example.weather.screens.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weather.R
import com.example.weather.databinding.FragmentEditProfileBinding
import com.example.weather.repository.Singletons
import com.example.weather.utils.observeEvent
import com.example.weather.utils.viewModelCreator


class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {

    private lateinit var binding: FragmentEditProfileBinding
    private val viewModel by viewModelCreator{ EditProfileViewModel(Singletons.accountsRepository)}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditProfileBinding.bind(view)

        observeState()
        observeCurrentAccount()
        observeNavigate()

        binding.saveButton.setOnClickListener { onSaveButtonPressedFragment() }
        binding.cancelButton.setOnClickListener { onCancelButtonPressed() }

    }

    private fun observeState() = viewModel.state.observe(viewLifecycleOwner){
        binding.usernameTextInput.error = if (it.name) getString(R.string.field_is_empty) else null

        binding.usernameTextInput.isEnabled = !it.InProgress
        binding.saveButton.isEnabled = !it.InProgress
        binding.progressBar.visibility = if (it.InProgress) View.VISIBLE else View.INVISIBLE
    }

    private fun observeCurrentAccount() = viewModel.currentAccount.observe(viewLifecycleOwner){
        binding.usernameEditText.setText(it.username)
    }

    private fun observeNavigate() = viewModel.navigate.observeEvent(viewLifecycleOwner) {
        findNavController().popBackStack()
    }

    private fun onSaveButtonPressedFragment(){
        viewModel.onSaveButtonPressedVM(
            binding.usernameEditText.text.toString()
        )
    }

    private fun onCancelButtonPressed(){
        findNavController().popBackStack()
    }
}