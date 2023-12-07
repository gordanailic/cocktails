package com.example.cocktails.presentation.view.fragments.favorites.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.cocktails.data.models.FavoriteRecyclerViewItem
import com.example.cocktails.databinding.ItemAlcoholicBinding
import com.example.cocktails.databinding.ItemFavoriteBinding
import com.example.cocktails.presentation.view.fragments.favorites.recycler.diff.FavoriteRecyclerViewItemDiffCallback
import com.example.cocktails.presentation.view.fragments.favorites.recycler.viewholder.FavoriteRecyclerViewHolder

class FavoriteRecyclerViewAdapter :
    ListAdapter<FavoriteRecyclerViewItem, FavoriteRecyclerViewHolder>(
        FavoriteRecyclerViewItemDiffCallback()
    ) {

    companion object {
        const val ALCOHOLIC_VIEW = 1
        const val FAVORITE_VIEW = 2
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteRecyclerViewHolder {

        return when (viewType) {
            ALCOHOLIC_VIEW -> FavoriteRecyclerViewHolder.AlcoholicViewHolder(
                ItemAlcoholicBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            FAVORITE_VIEW -> FavoriteRecyclerViewHolder.FavoritesViewHolder(
                ItemFavoriteBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            else -> throw IllegalArgumentException("Invalid ViewType Provided")
        }
    }

    override fun getItemCount() = currentList.size

    override fun onBindViewHolder(holder: FavoriteRecyclerViewHolder, position: Int) {
        when (holder) {
            is FavoriteRecyclerViewHolder.FavoritesViewHolder -> holder.bind(getItem(position) as FavoriteRecyclerViewItem.Favorite)
            is FavoriteRecyclerViewHolder.AlcoholicViewHolder -> holder.bind(getItem(position) as FavoriteRecyclerViewItem.Alcoholic)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is FavoriteRecyclerViewItem.Alcoholic -> ALCOHOLIC_VIEW
            is FavoriteRecyclerViewItem.Favorite -> FAVORITE_VIEW
            else -> throw IllegalArgumentException("Invalid item type")
        }
    }
}