package com.example.cocktails.presentation.fragments.profile.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.cocktails.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    fun removeFromSharedPref() {
        val editor = sharedPreferences.edit()
        editor.remove(Constants.loginKey)
        editor.apply()
    }
}
