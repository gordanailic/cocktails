package com.example.cocktails.presentation.fragments.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.example.cocktails.R
import com.example.cocktails.databinding.FragmentRegisterBinding
import com.example.cocktails.presentation.fragments.authentication.viewmodel.AuthViewModel

class RegistrationFragment : Fragment(R.layout.fragment_register) {

    private val authViewModel: AuthViewModel by hiltNavGraphViewModels(R.id.registerFragment)
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val emailPattern: Regex = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-z]+")
    private val passwordPatter: Regex = Regex("^(?=.*[A-Za-z])(?=.*\\d).{6,}\$")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener() {
        binding.buttonRegister.setOnClickListener {
            val name = binding.name.text.toString().trim()
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()
            var isSaved = false
            if (name.isEmpty()) {
                binding.name.error = resources.getString(R.string.invalid_name)
            }
            if (!checkEmail(email)) {
                binding.email.error = resources.getString(R.string.invalid_email)
            }

            if (!checkPassword(password)) {
                binding.password.error = resources.getString(R.string.invalid_password)
            }
            if (checkEmail(email) && checkPassword(password) && name.isNotEmpty()) {
                isSaved = authViewModel.saveUserData(name, email, password)
            }
            if (isSaved) {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            } else {
                binding.email.error = resources.getString(R.string.unique_email)
            }
        }

        binding.buttonLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun checkEmail(email: String): Boolean {
        return email.matches(emailPattern) && email.isNotEmpty()
    }

    private fun checkPassword(password: String): Boolean {
        return password.matches(passwordPatter) && password.isNotEmpty()
    }

}

