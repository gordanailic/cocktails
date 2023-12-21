package com.example.cocktails.presentation.fragments.details.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktails.Constants
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
    private val _cocktailsDetails = MutableLiveData<Resource<CocktailDetailsData>>()
    val cocktailsDetails: LiveData<Resource<CocktailDetailsData>> get() = _cocktailsDetails

    data class CocktailDetailsData(
        val name: String,
        val alcoholic: String,
        val category: String,
        val glass: String,
        val ingredients: String,
        val instruction: String,
        val image: String
    )


    private val cocktailState: MutableLiveData<CocktailState> = MutableLiveData()
    private val stringBuilderIngredient = StringBuilder()


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
                val cocktailDetailsMap = getNonNullFields(cocktails)
                val cocktailDetailsData = CocktailDetailsData(
                    cocktailDetailsMap[Constants.nameDrinkKey] ?: "",
                    cocktailDetailsMap[Constants.alcoholicKey] ?: "",
                    cocktailDetailsMap[Constants.categoryKey] ?: "",
                    cocktailDetailsMap[Constants.glassKey] ?: "",
                    setDetails(cocktailDetailsMap),
                    cocktailDetailsMap[Constants.instructionsKey] ?: "",
                    cocktailDetailsMap[Constants.drinkThumbKey] ?: ""
                )
                _cocktailsDetails.value = Resource.Success(cocktailDetailsData)

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

    private fun getNonNullFields(cocktailDetailsList: List<CocktailDetails>): Map<String, String> {
        return cocktailDetailsList.firstOrNull()?.let { cocktailDetails ->
            val fields = CocktailDetails::class.java.declaredFields
            val result = mutableMapOf<String, String>()

            for (field in fields) {
                field.isAccessible = true
                val value = field.get(cocktailDetails)
                if (value != null) {
                    result[field.name] = value.toString()
                }
            }

            result.ifEmpty { null }
        } ?: mapOf()
    }

    fun createCocktail(
        cocktailDetailsData: CocktailDetailsData,
        id: Int,
        argFavorite: Boolean,
        argEmail: String
    ): Cocktail {
        return Cocktail(
            id,
            cocktailDetailsData.name,
            cocktailDetailsData.image,
            cocktailDetailsData.alcoholic,
            argFavorite,
            argEmail
        )
    }

    private fun setDetails(cocktailDetailsMap: Map<String, String>): String {
        for (i in 1..15) {
            val ingredientKey = "strIngredient$i"
            val measureKey = "strMeasure$i"

            val ingredient = cocktailDetailsMap[ingredientKey]
            val measure = cocktailDetailsMap[measureKey]


            if (!ingredient.isNullOrEmpty()) {
                stringBuilderIngredient.append("$ingredient")
                if (!measure.isNullOrEmpty()) {
                    stringBuilderIngredient.append("(")
                    stringBuilderIngredient.append("$measure")
                    stringBuilderIngredient.append(")\n")
                }
            }
        }

        return stringBuilderIngredient.toString()
    }
}