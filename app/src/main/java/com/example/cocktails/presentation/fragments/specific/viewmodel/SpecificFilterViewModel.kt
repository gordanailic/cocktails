package com.example.cocktails.presentation.fragments.specific.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktails.R
import com.example.cocktails.data.models.Filter
import com.example.cocktails.data.models.Resource
import com.example.cocktails.data.repositories.CocktailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpecificFilterViewModel @Inject constructor(
    private val application: Application, private val cocktailRepository: CocktailRepository
) : ViewModel() {

    private val _filters = MutableLiveData<Resource<List<Filter>>>()
    val cocktails: LiveData<Resource<List<Filter>>> get() = _filters
    private val filterMap: MutableMap<String, String> = mutableMapOf()


    private val handler = CoroutineExceptionHandler { _, exception ->
        _filters.value =
            Resource.Error(application.applicationContext.resources.getString(R.string.failed_fetch) + exception.message)
    }

    fun fetchCocktailsByCategory(filter: String) {
        filterMap[filter] = "list"

        viewModelScope.launch(handler) {
            _filters.value = Resource.Loading()

            val response = cocktailRepository.getSpecificCategory(filterMap)
            if (response.isSuccessful) {

                val filters = response.body()?.drinks ?: emptyList()
                filters.forEach {
                    it.filterParam = filter
                }
                _filters.value = Resource.Success(filters)
            } else {
                _filters.value =
                    Resource.Error(application.applicationContext.resources.getString(R.string.failed_fetch))
            }
        }
    }

}