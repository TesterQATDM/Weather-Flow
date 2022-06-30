package com.example.weather.screens.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.weather.R
import com.example.weather.databinding.FragmentSignInBinding
import com.example.weather.utils.observeEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private lateinit var binding: FragmentSignInBinding
    private val viewModel by viewModels<SignInViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignInBinding.bind(view)
        observeState()
        observeShowAuthError()
        observeNavigate()

        binding.signInButton.setOnClickListener {signInButton()}
        binding.signUpButton.setOnClickListener {signUpButton()}

    }

    private fun observeState() = viewModel.state.observe(viewLifecycleOwner){
        binding.emailTextInput.error = if (it.emailEmpty) getString(R.string.field_is_empty) else null
        binding.passwordTextInput.error = if (it.passwordEmpty) getString(R.string.field_is_empty) else null

        binding.emailTextInput.isEnabled = !it.InProgress
        binding.passwordTextInput.isEnabled = !it.InProgress
        binding.signInButton.isEnabled = !it.InProgress
        binding.signUpButton.isEnabled = !it.InProgress
        binding.progressBar.visibility = if (it.InProgress) View.VISIBLE else View.INVISIBLE
    }

    private fun observeShowAuthError() = viewModel.clearFields.observeEvent(viewLifecycleOwner) {
        Toast.makeText(requireContext(), R.string.invalid_email_or_password, Toast.LENGTH_SHORT).show()
        binding.emailEditText.text?.clear()
        binding.passwordEditText.text?.clear()
    }

    private fun observeNavigate() = viewModel.navigateToTabFragment.observeEvent(viewLifecycleOwner) {
        findNavController().navigate(R.id.action_signInFragment_to_tabsFragment)
    }

    private fun signInButton(){
        viewModel.signInAccount(
            email = binding.emailEditText.text.toString(),
            password = binding.passwordEditText.text.toString()
        )
    }

    private fun signUpButton(){
        val email = binding.emailEditText.text.toString()
        val emailArg = email.ifBlank { null }
        val direction = SignInFragmentDirections.actionSignInFragmentToSignUpFragment(emailArg)
        findNavController().navigate(direction)
    }
}