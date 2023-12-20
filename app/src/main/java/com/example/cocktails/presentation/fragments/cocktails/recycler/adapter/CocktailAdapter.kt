package com.example.cocktails.presentation.fragments.cocktails.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.example.cocktails.R
import com.example.cocktails.data.models.Cocktail
import com.example.cocktails.databinding.ItemCocktailBinding
import com.example.cocktails.presentation.fragments.cocktails.recycler.diff.CocktailDiffcallback
import com.example.cocktails.presentation.fragments.cocktails.recycler.viewholder.CocktailViewHolder

class CocktailAdapter : ListAdapter<Cocktail, CocktailViewHolder>(CocktailDiffcallback()) {
    var onImageClickListener: ((Cocktail, Int) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        val itemBinding =
            ItemCocktailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CocktailViewHolder(itemBinding).apply {
            onViewHolderCreated(this, itemBinding)

        }
    }

    private fun onViewHolderCreated(
        cocktailViewHolder: CocktailViewHolder,
        binding: ItemCocktailBinding
    ) {
        binding.favoriteButton.setOnClickListener {
            val cocktailItem = getItem(cocktailViewHolder.bindingAdapterPosition)
            val imageResourceId = if (cocktailItem.favorite) {
                R.drawable.imageview_unchecked
            } else {
                R.drawable.imageview_checked
            }

            Glide.with(binding.favoriteButton.context).load(imageResourceId)
                .into(binding.favoriteButton)
            onImageClickListener?.invoke(
                getItem(cocktailViewHolder.bindingAdapterPosition),
                cocktailViewHolder.bindingAdapterPosition
            )
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}


