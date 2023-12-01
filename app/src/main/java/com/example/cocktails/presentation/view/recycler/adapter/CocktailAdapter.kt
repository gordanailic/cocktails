package com.example.cocktails.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.cocktails.data.models.Cocktail
import com.example.cocktails.databinding.ItemCocktailBinding
import com.example.cocktails.presentation.view.recycler.diff.CoctailDiffcallback
import com.example.cocktails.presentation.view.recycler.viewholder.CocktailViewHolder

class CocktailAdapter(
    var cocktails: List<Cocktail>
) : ListAdapter<Cocktail, CocktailViewHolder>(CoctailDiffcallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        val itemBinding =
            ItemCocktailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CocktailViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return cocktails.size
    }

    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        holder.bind(cocktails[position])
    }

}


