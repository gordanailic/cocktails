package com.example.cocktails.data.models

sealed class FavoriteRecyclerViewItem {

    class Category(
        val alcoholic: String?
    ) : FavoriteRecyclerViewItem()

    class Favorite(
        val id: Int,
        val name: String,
        val image: String,
        var favorite: Boolean,
        val alcoholic: String?,
    ) : FavoriteRecyclerViewItem()
}