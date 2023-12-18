package com.example.cocktails.presentation.fragments.specific.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.cocktails.data.models.Filter
import com.example.cocktails.databinding.ItemFilterBinding

class SpecificFilterViewHolder(
    private val itemBinding: ItemFilterBinding,
) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(filter: Filter) {
        itemBinding.item.text = filter.getLabel()
    }
}
