package com.example.cocktails.presentation.view.fragments.favorites.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.cocktails.data.models.FavoriteRecyclerViewItem
import com.example.cocktails.databinding.ItemAlcoholicBinding
import com.example.cocktails.databinding.ItemFavoriteBinding

sealed class FavoriteRecyclerViewHolder(itemBinding: ViewBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    class AlcoholicViewHolder(private val itemBinding: ItemAlcoholicBinding) :
        FavoriteRecyclerViewHolder(itemBinding) {
        fun bind(alcoholic: FavoriteRecyclerViewItem.Alcoholic) {
            itemBinding.alcoholic.text = alcoholic.alcoholic
        }
    }

    class FavoritesViewHolder(private val itemBinding: ItemFavoriteBinding) :
        FavoriteRecyclerViewHolder(itemBinding) {
        fun bind(favorite: FavoriteRecyclerViewItem.Favorite) {
            Glide.with(itemBinding.cocktail.context).load(favorite.image).into(itemBinding.cocktail)
            itemBinding.nameCocktail.text = favorite.name
        }
    }
}