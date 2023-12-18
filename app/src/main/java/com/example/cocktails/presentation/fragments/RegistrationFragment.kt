package com.example.cocktails.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.cocktails.R
import com.example.cocktails.databinding.FragmentRegisterBinding

class RegistrationFragment : Fragment(R.layout.fragment_register) {

    private var _binding: FragmentRegisterBinding? = null
    private lateinit var nameEditText: EditText
    private lateinit var name: String
    private lateinit var emailEditText: EditText
    private lateinit var email: String
    private lateinit var passeordEditText: EditText
    private lateinit var password: String
    private lateinit var registerButton: Button
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
        initUI()
        initListener()

    }

    private fun initUI() {
        nameEditText = binding.name
        emailEditText = binding.email
        passeordEditText = binding.password
        registerButton = binding.buttonRegister
    }

    private fun initListener() {
        registerButton.setOnClickListener {
            name = binding.name.text.toString()
            email = binding.email.text.toString().trim()
            password = binding.password.text.toString()

            if (name.isEmpty()) {
                nameEditText.error = resources.getString(R.string.invalid_name)
            }
            if (!checkEmail(email)) {
                emailEditText.error = resources.getString(R.string.invalid_email)
            }

            if (!checkPassword(password)) {
                passeordEditText.error = resources.getString(R.string.invalid_password)
            }
            if (checkEmail(email) && checkPassword(password) && name.isNotEmpty()) {
                saveUserData()
            }
        }
    }

    private fun checkEmail(email: String): Boolean {
        return email.matches(emailPattern) && email.isNotEmpty()
    }

    private fun checkPassword(password: String): Boolean {
        return password.matches(passwordPatter) && password.isNotEmpty()
    }

    private fun saveUserData() {
        val sharedPreferences =
            activity?.getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putString("name", name)
        editor?.putString("email", email)
        editor?.putString("password", password)
        editor?.apply()
    }

}

