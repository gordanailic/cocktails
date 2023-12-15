package com.example.cocktails.presentation.fragments.filter.recycler.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.cocktails.R
import com.example.cocktails.databinding.ItemFilterBinding

class FilterAdapter(context: Activity, private val arrayList: ArrayList<String>) :
    ArrayAdapter<String>(context, R.layout.item_filter, arrayList) {
    var onArrowClickListener: ((String, Int) -> Unit)? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemFilterBinding = if (convertView == null) {
            val inflater = LayoutInflater.from(parent.context)
            ItemFilterBinding.inflate(inflater, parent, false)
        } else {
            val existingBinding = convertView.tag as? ItemFilterBinding
            existingBinding ?: ItemFilterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        }

        val name: TextView = binding.item
        name.text = arrayList[position]

        binding.root.setOnClickListener {
            onArrowClickListener?.invoke(arrayList[position], position)
        }

        convertView?.tag = binding
        return binding.root
    }
}