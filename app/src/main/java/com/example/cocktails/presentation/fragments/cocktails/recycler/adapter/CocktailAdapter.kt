package com.example.cocktails.presentation.fragments.cocktails.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.cocktails.data.models.Cocktail
import com.example.cocktails.databinding.ItemCocktailBinding
import com.example.cocktails.presentation.fragments.cocktails.recycler.diff.CocktailDiffcallback
import com.example.cocktails.presentation.fragments.cocktails.recycler.viewholder.CocktailViewHolder

class CocktailAdapter: ListAdapter<Cocktail, CocktailViewHolder>(CocktailDiffcallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        val itemBinding =
            ItemCocktailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CocktailViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}


