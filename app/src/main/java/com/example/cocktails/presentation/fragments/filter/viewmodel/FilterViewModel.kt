package com.example.cocktails.presentation.fragments.filter.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cocktails.R
import com.example.cocktails.data.models.Cocktail
import com.example.cocktails.data.models.Resource
import com.example.cocktails.data.repositories.CocktailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val application: Application, private val cocktailRepository: CocktailRepository
) : ViewModel() {
    private val _cocktails = MutableLiveData<Resource<List<Cocktail>>>()
    val cocktails: LiveData<Resource<List<Cocktail>>> get() = _cocktails

    private val handler = CoroutineExceptionHandler { _, exception ->
        _cocktails.value =
            Resource.Error(application.applicationContext.resources.getString(R.string.failed_fetch) + exception.message)
    }


}