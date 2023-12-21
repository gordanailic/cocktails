package com.example.cocktails.presentation.fragments.favorites.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.cocktails.data.models.FavoriteRecyclerViewItem
import com.example.cocktails.data.repositories.CocktailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val cocktailRepository: CocktailRepository
) : ViewModel() {
    var favoritesList: LiveData<List<FavoriteRecyclerViewItem>> = MutableLiveData()
    fun getFavorites(email: String) {
        favoritesList =
            cocktailRepository.getFavorites(email).map { list ->
                val listItem: MutableList<FavoriteRecyclerViewItem> = mutableListOf()
                list.forEach { cocktail ->
                    if (listItem.any { favItem ->
                            favItem is FavoriteRecyclerViewItem.Category && favItem.alcoholic == cocktail.alcoholic
                        }) {
                        val favoriteItem: FavoriteRecyclerViewItem.Favorite =
                            FavoriteRecyclerViewItem.Favorite(
                                cocktail.id, cocktail.name, cocktail.image, cocktail.favorite, cocktail.alcoholic
                            )
                        listItem.add(favoriteItem)

                    } else {
                        val labelItem: FavoriteRecyclerViewItem.Category =
                            FavoriteRecyclerViewItem.Category(cocktail.alcoholic)
                        listItem.add(labelItem)
                        val favoriteItem: FavoriteRecyclerViewItem.Favorite =
                            FavoriteRecyclerViewItem.Favorite(
                                cocktail.id, cocktail.name, cocktail.image, cocktail.favorite, cocktail.alcoholic
                            )
                        listItem.add(favoriteItem)
                    }

                }

                return@map listItem
            }
    }


}