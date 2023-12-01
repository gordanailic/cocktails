package com.example.cocktails.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cocktails.data.models.Cocktail
import com.example.cocktails.databinding.ItemCocktailBinding

class CocktailViewHolder(private val itemBinding: ItemCocktailBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(cocktail: Cocktail) {
        Glide.with(itemBinding.cocktail.context)
            .load(cocktail.image)
            .into(itemBinding.cocktail)
        itemBinding.nameCocktail.text = cocktail.name
    }

}