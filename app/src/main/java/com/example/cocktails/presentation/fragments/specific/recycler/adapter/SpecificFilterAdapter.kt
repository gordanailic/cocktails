package com.example.cocktails.presentation.fragments.specific.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.cocktails.data.models.Filter
import com.example.cocktails.databinding.ItemFilterBinding
import com.example.cocktails.presentation.fragments.specific.recycler.diff.SpecificFilterDiffcallback
import com.example.cocktails.presentation.fragments.specific.recycler.viewholder.SpecificFilterViewHolder

class SpecificFilterAdapter :
    ListAdapter<Filter, SpecificFilterViewHolder>(SpecificFilterDiffcallback()) {

    var onItemClickListener: ((Filter, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecificFilterViewHolder {
        val itemBinding =
            ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SpecificFilterViewHolder(itemBinding).apply {
            onViewHolderCreated(this, itemBinding)
        }

    }

    private fun onViewHolderCreated(
        specificFilterViewHolder: SpecificFilterViewHolder,
        binding: ItemFilterBinding
    ) {
        binding.root.setOnClickListener {
            onItemClickListener?.invoke(
                getItem(specificFilterViewHolder.bindingAdapterPosition),
                specificFilterViewHolder.bindingAdapterPosition
            )
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: SpecificFilterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}