package com.example.cocktails.presentation.fragments.cocktails.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktails.R
import com.example.cocktails.data.models.Cocktail
import com.example.cocktails.data.models.Resource
import com.example.cocktails.data.repositories.CocktailRepository
import com.example.cocktails.presentation.fragments.favorites.viewmodel.CocktailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailViewModel @Inject constructor(
    private val application: Application, private val cocktailRepository: CocktailRepository
) : ViewModel() {
    companion object {
        private const val debouncePeriod = 500L
    }

    private val cocktailState: MutableLiveData<CocktailState> = MutableLiveData()

    private val _cocktails = MutableLiveData<Resource<List<Cocktail>>>()

    private var favoritesItem: List<Int> = mutableListOf()
    private val filterMap: MutableMap<String, String> = mutableMapOf()


    val cocktails: LiveData<Resource<List<Cocktail>>> get() = _cocktails


    private var searchJob: Job? = null


    private val handler = CoroutineExceptionHandler { _, exception ->
        _cocktails.value =
            Resource.Error(application.applicationContext.resources.getString(R.string.failed_fetch) + exception.message)
    }

    fun fetchAllCocktails(query: String, email: String) {
        searchJob?.cancel()

        searchJob = viewModelScope.launch(handler) {
            _cocktails.value = Resource.Loading()
            if (query.isNotEmpty()) {
                delay(debouncePeriod)
            }
            val response = cocktailRepository.getCocktails(query)
            if (response.isSuccessful) {
                val cocktails = response.body()?.drinks ?: emptyList()
                getFavoritesIDs(email)
                cocktails.forEach { cocktail ->
                    if (favoritesItem.contains(cocktail.id)) {
                        cocktail.favorite = true
                    }
                }
                _cocktails.value = Resource.Success(cocktails)
            } else {
                _cocktails.value =
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

    private suspend fun getFavoritesIDs(email: String) {
        favoritesItem = cocktailRepository.getFavoritesIDs(email)
    }

    fun fetchCocktailsByFilter(argSpec: String, filterBy: String, email: String) {
        filterMap[filterBy] = argSpec

        viewModelScope.launch(handler) {
            _cocktails.value = Resource.Loading()
            val response = cocktailRepository.getCocktailsByFilter(filterMap)
            if (response.isSuccessful) {
                val cocktails = response.body()?.drinks ?: emptyList()
                getFavoritesIDs(email)
                cocktails.forEach { cocktail ->
                    if (favoritesItem.contains(cocktail.id)) {
                        cocktail.favorite = true
                    }
                }
                cocktails.forEach {
                    if (it.alcoholic.isNullOrEmpty()) {
                        it.alcoholic = "Other"
                    }
                }
                _cocktails.value = Resource.Success(cocktails)
            } else {
                _cocktails.value =
                    Resource.Error(application.applicationContext.resources.getString(R.string.failed_fetch))
            }
        }
    }
}



