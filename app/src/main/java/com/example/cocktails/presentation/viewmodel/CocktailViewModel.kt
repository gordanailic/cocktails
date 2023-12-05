package com.example.cocktails.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktails.R
import com.example.cocktails.data.models.Cocktail
import com.example.cocktails.data.models.Resource
import com.example.cocktails.data.repositories.CocktailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailViewModel @Inject constructor(
    private val application: Application,
    private val cocktailRepository: CocktailRepository
) : ViewModel() {
    private val _cocktails = MutableLiveData<Resource<List<Cocktail>>>()
    val cocktails: LiveData<Resource<List<Cocktail>>> get() = _cocktails

    private val handler = CoroutineExceptionHandler { _, exception ->
        _cocktails.value =
            Resource.Error(application.applicationContext.resources.getString(R.string.failed_fetch) + exception.message)
    }

    fun fetchAllCocktails() {
        viewModelScope.launch(handler) {
            _cocktails.value = Resource.Loading()
            val response = cocktailRepository.getAllCocktails()
            if (response.isSuccessful) {
                val cocktails = response.body()?.drinks ?: emptyList()
                _cocktails.value = Resource.Success(cocktails)
            } else {
                _cocktails.value =
                    Resource.Error(application.applicationContext.resources.getString(R.string.failed_fetch))
            }
        }
    }

}


