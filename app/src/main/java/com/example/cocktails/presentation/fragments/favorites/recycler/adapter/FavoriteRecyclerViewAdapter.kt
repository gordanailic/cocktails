package com.example.cocktails.presentation.fragments.favorites.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktails.data.models.FavoriteRecyclerViewItem
import com.example.cocktails.databinding.ItemAlcoholicBinding
import com.example.cocktails.databinding.ItemFavoriteBinding
import com.example.cocktails.presentation.fragments.favorites.recycler.diff.FavoriteRecyclerViewItemDiffCallback
import com.example.cocktails.presentation.fragments.favorites.recycler.viewholder.FavoriteRecyclerViewHolder

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

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        (recyclerView.layoutManager as GridLayoutManager).spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (getItemViewType(position)) {
                        ALCOHOLIC_VIEW -> 2
                        FAVORITE_VIEW -> 1
                        else -> throw IllegalArgumentException("Invalid ViewType Provided")
                    }
                }
            }
    }
}