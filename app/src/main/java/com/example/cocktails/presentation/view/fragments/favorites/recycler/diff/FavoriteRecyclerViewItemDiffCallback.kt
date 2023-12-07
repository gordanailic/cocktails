package com.example.cocktails.presentation.view.fragments.favorites.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import com.example.cocktails.data.models.FavoriteRecyclerViewItem

class FavoriteRecyclerViewItemDiffCallback : DiffUtil.ItemCallback<FavoriteRecyclerViewItem>() {
    override fun areItemsTheSame(
        oldItem: FavoriteRecyclerViewItem, newItem: FavoriteRecyclerViewItem
    ): Boolean {
        return when {
            oldItem is FavoriteRecyclerViewItem.Alcoholic && newItem is FavoriteRecyclerViewItem.Alcoholic -> true
            oldItem is FavoriteRecyclerViewItem.Favorite && newItem is FavoriteRecyclerViewItem.Favorite -> oldItem.id == newItem.id

            else -> false
        }
    }

    override fun areContentsTheSame(
        oldItem: FavoriteRecyclerViewItem, newItem: FavoriteRecyclerViewItem
    ): Boolean {
        return when {
            oldItem is FavoriteRecyclerViewItem.Alcoholic && newItem is FavoriteRecyclerViewItem.Alcoholic ->
                oldItem.alcoholic == newItem.alcoholic

            oldItem is FavoriteRecyclerViewItem.Favorite && newItem is FavoriteRecyclerViewItem.Favorite ->
                oldItem.name == newItem.name && oldItem.image == newItem.image && oldItem.alcoholic == newItem.alcoholic

            else -> false
        }
    }
}