package com.example.cocktails.presentation.fragments.profile.viewmodel

import android.content.SharedPreferences
import android.net.Uri
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

    fun getValueFromSharedPref(key: String, email: String): String? {
        return sharedPreferences.getString(key + email, null)
    }

    fun updateValueInSharedPref(key: String, email: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key + email, value)
        editor.apply()
    }

    fun saveImageUriToSharedPref(uri: Uri) {
        val uriString = uri.toString()
        val editor = sharedPreferences.edit()
        editor?.putString(Constants.imageKey, uriString)
        editor?.apply()
    }

    fun loadImageUriFromSharedPref(): Uri? {
        val uriString = sharedPreferences.getString(Constants.imageKey, null)
        return uriString?.let { Uri.parse(it) }
    }
}
