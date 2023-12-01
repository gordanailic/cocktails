package com.example.cocktails.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import com.example.cocktails.data.models.Cocktail

class CoctailDiffcallback : DiffUtil.ItemCallback<Cocktail>() {

    override fun areItemsTheSame(oldItem: Cocktail, newItem: Cocktail): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Cocktail, newItem: Cocktail): Boolean {
        return oldItem.name == newItem.name &&
                oldItem.image == newItem.image &&
                oldItem.favorite == newItem.favorite
    }

}