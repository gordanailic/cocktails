package com.example.cocktails.presentation.fragments.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cocktails.Constants
import com.example.cocktails.R
import com.example.cocktails.databinding.FragmentFilterBinding
import com.example.cocktails.presentation.fragments.filter.recycler.adapter.FilterAdapter


class FilterFragment : Fragment(R.layout.fragment_filter) {

    private var _binding: FragmentFilterBinding? = null

    private val binding get() = _binding!!
    private lateinit var adapter: FilterAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = FilterAdapter(requireActivity(), Constants.names)
        binding.listItem.adapter = adapter

        initUI()
    }

    private fun initUI() {
        adapter.onArrowClickListener = { _: String, position: Int ->
            val filterBy: String = when (position) {
                0 -> {
                    "a"
                }

                1 -> {
                    "c"
                }

                2 -> {
                    "g"
                }

                3 -> {
                    "i"
                }

                else -> {
                    "a"
                }
            }

            val args = bundleOf("argFilter" to filterBy)
            findNavController().navigate(R.id.action_filterFragment_to_specificFilterFragment, args)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}