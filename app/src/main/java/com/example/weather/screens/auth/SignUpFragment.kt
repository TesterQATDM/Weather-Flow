package com.example.weather.screens.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.weather.R
import com.example.weather.databinding.FragmentSignUpBinding
import com.example.weather.repository.Repositories
import com.example.weather.repository.account.room.entities.SignUpData
import com.example.weather.utils.observeEvent
import com.example.weather.utils.viewModelCreator

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private lateinit var binding: FragmentSignUpBinding
    private val viewModel by viewModelCreator { SignUpViewModel(Repositories.accountRepository) }
    private val args by navArgs<SignUpFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpBinding.bind(view)

        observeState()
        observeShowUpAuthError()
        observeAutUphError()
        observeNavigate()

        if (savedInstanceState == null && args.emailArg != null){
            binding.emailEditText.setText(args.emailArg)
        }
        binding.createAccountButton.setOnClickListener {createAccountFragment()}

    }

    private fun observeState() = viewModel.state.observe(viewLifecycleOwner){
        binding.emailTextInput.error = if (it.emailEmpty) getString(R.string.field_is_empty) else null
        binding.usernameTextInput.error = if (it.nameEmpty) getString(R.string.field_is_empty) else null
        binding.passwordTextInput.error = if (it.passwordEmpty) getString(R.string.field_is_empty) else null
        binding.repeatPasswordTextInput.error = if (it.repeatPasswordEmpty) getString(R.string.field_is_empty) else null

        binding.emailTextInput.isEnabled = !it.InProgress
        binding.usernameTextInput.isEnabled = !it.InProgress
        binding.passwordTextInput.isEnabled = !it.InProgress
        binding.repeatPasswordTextInput.isEnabled = !it.InProgress

        binding.createAccountButton.isEnabled = !it.InProgress
        binding.progressBar.visibility = if (it.InProgress) View.VISIBLE else View.INVISIBLE
    }

    private fun observeShowUpAuthError() = viewModel.clearFieldsPassword.observeEvent(viewLifecycleOwner) {
        Toast.makeText(requireContext(), R.string.password_mismatch, Toast.LENGTH_SHORT).show()
        binding.passwordEditText.text?.clear()
        binding.repeatPasswordEditText.text?.clear()
    }

    private fun observeAutUphError() = viewModel.clearFieldsEmail.observeEvent(viewLifecycleOwner) {
        Toast.makeText(requireContext(), R.string.account_already_exists, Toast.LENGTH_SHORT).show()
        binding.emailEditText.text?.clear()
    }

    private fun observeNavigate() = viewModel.navigateToSignInFragment.observeEvent(viewLifecycleOwner) {
        findNavController().popBackStack()
    }

    private fun createAccountFragment(){
        val signUpData = SignUpData(
            email = binding.emailEditText.text.toString(),
            username = binding.usernameEditText.text.toString(),
            password = binding.passwordEditText.text.toString(),
            repeatPassword = binding.repeatPasswordEditText.text.toString()
        )
        viewModel.createAccountVM(signUpData)
    }

}