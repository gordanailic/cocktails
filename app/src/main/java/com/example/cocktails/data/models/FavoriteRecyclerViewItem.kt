package com.example.cocktails.data.models

sealed class FavoriteRecyclerViewItem {

    class Category(
        val alcoholic: String?
    ) : FavoriteRecyclerViewItem()

    class Favorite(
        val id: Int,
        val name: String,
        val image: String,
        val alcoholic: String?,
    ) : FavoriteRecyclerViewItem()
}