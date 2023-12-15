package com.example.cocktails.presentation.fragments.specific.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import com.example.cocktails.data.models.Filter

class SpecificFilterDiffcallback  : DiffUtil.ItemCallback<Filter>() {
    override fun areItemsTheSame(oldItem: Filter, newItem: Filter): Boolean {
        TODO("Not yet implemented")
    }

    override fun areContentsTheSame(oldItem: Filter, newItem: Filter): Boolean {
        TODO("Not yet implemented")
    }
}