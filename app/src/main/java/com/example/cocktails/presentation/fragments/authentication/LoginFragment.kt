package com.example.cocktails.presentation.fragments.authentication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.example.cocktails.Constants
import com.example.cocktails.R
import com.example.cocktails.databinding.FragmentLoginBinding
import com.example.cocktails.presentation.activities.MainActivity
import com.example.cocktails.presentation.fragments.authentication.viewmodel.AuthViewModel


class LoginFragment : Fragment(R.layout.fragment_login) {

    private val authViewModel: AuthViewModel by hiltNavGraphViewModels(R.id.loginFragment)
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener() {
        var isRegistered: Boolean
        binding.buttonLogin.setOnClickListener {
            isRegistered = authViewModel.checkData(
                binding.email.text.toString().trim(), binding.password.text.toString().trim()
            )
            if (isRegistered) {
                binding.incorrect.visibility = View.GONE
                val intent = Intent(activity, MainActivity::class.java)
                intent.putExtra(Constants.emailKey, binding.email.text.toString())
                startActivity(intent)
                activity?.finish()
            } else {
                binding.incorrect.visibility = View.VISIBLE
            }
        }
        binding.buttonRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

    }

}