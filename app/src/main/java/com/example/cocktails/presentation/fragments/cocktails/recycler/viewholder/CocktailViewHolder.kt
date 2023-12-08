package com.example.cocktails.presentation.fragments.cocktails.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cocktails.R
import com.example.cocktails.data.models.Cocktail
import com.example.cocktails.databinding.ItemCocktailBinding

class CocktailViewHolder(private val itemBinding: ItemCocktailBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(cocktail: Cocktail) {
        Glide.with(itemBinding.cocktail.context).load(cocktail.image).into(itemBinding.cocktail)
        itemBinding.nameCocktail.text = cocktail.name
        if (cocktail.favorite) {
            setImage(R.drawable.imageview_checked)
        } else {
            setImage(R.drawable.imageview_unchecked)
        }
    }

    fun setImage(image: Int) {
        Glide.with(itemBinding.favoriteButton.context).
        load(image).
        into(itemBinding.favoriteButton)
    }

}