package com.example.cocktails.presentation.fragments

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.cocktails.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    fun saveUserData(name: String, email: String, password: String): Boolean {
        return if (sharedPreferences.getString(Constants.emailKey + email, null).equals(email)) {
            false
        } else {
            val editor = sharedPreferences.edit()
            editor?.putString(Constants.nameKey + name, name)
            editor?.putString(Constants.emailKey + email, email)
            editor?.putString(Constants.passwordKey + password, password)
            editor?.apply()
            true
        }
    }
}