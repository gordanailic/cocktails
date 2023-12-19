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
            editor?.putString(Constants.nameKey + email, name)
            editor?.putString(Constants.emailKey + email, email)
            editor?.putString(Constants.passwordKey + email, password)
            editor?.apply()
            true
        }
    }

    fun checkData(email: String, password: String): Boolean{
        return if(sharedPreferences.getString(Constants.emailKey + email, null).equals(email)
            && sharedPreferences.getString(Constants.passwordKey + email, null).equals(password)){
            val editor = sharedPreferences.edit()
            editor.putBoolean("login", true)
            editor.apply()
            true
        }else{
            false
        }
    }
}