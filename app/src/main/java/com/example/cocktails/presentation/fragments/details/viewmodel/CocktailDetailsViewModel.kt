package com.example.cocktails.presentation.fragments.details.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktails.R
import com.example.cocktails.data.models.Cocktail
import com.example.cocktails.data.models.CocktailDetails
import com.example.cocktails.data.models.Resource
import com.example.cocktails.data.repositories.CocktailRepository
import com.example.cocktails.presentation.fragments.favorites.viewmodel.CocktailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailDetailsViewModel @Inject constructor(
    private val application: Application, private val cocktailRepository: CocktailRepository
) : ViewModel() {
    private val _cocktailsDetails = MutableLiveData<Resource<List<CocktailDetails>>>()

    val cocktailsDetails: LiveData<Resource<List<CocktailDetails>>> get() = _cocktailsDetails
    private val cocktailState: MutableLiveData<CocktailState> = MutableLiveData()


    private val handler = CoroutineExceptionHandler { _, exception ->
        _cocktailsDetails.value =
            Resource.Error(application.applicationContext.resources.getString(R.string.failed_fetch) + exception.message)
    }
    fun fetchAllCocktailDetails(id: Int) {
        viewModelScope.launch(handler) {
            _cocktailsDetails.value = Resource.Loading()

            val response = cocktailRepository.getCocktailDetails(id)
            if (response.isSuccessful) {
                val cocktails = response.body()?.drinks ?: emptyList()

                _cocktailsDetails.value = Resource.Success(cocktails)
            } else {
                _cocktailsDetails.value =
                    Resource.Error(application.applicationContext.resources.getString(R.string.failed_fetch))
            }
        }
    }
    fun insertCocktail(cocktail: Cocktail) {
        viewModelScope.launch(handler) {
            val response = cocktailRepository.insertCocktail(cocktail)
            if (response > 0) {
                cocktailState.value = CocktailState.SuccessAddOrDelete
            } else {
                cocktailState.value =
                    CocktailState.Error(application.applicationContext.resources.getString(R.string.failed_fetch))
            }
        }
    }
    fun deleteCocktail(cocktail: Cocktail) {
        viewModelScope.launch(handler) {
            val response = cocktailRepository.deleteCocktail(cocktail)
            if (response > 0) {
                cocktailState.value = CocktailState.SuccessAddOrDelete
            } else {
                cocktailState.value =
                    CocktailState.Error(application.applicationContext.resources.getString(R.string.failed_fetch))
            }
        }
    }
}