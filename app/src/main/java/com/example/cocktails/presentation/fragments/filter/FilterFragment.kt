package com.example.cocktails.presentation.fragments.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.cocktails.R
import com.example.cocktails.databinding.FragmentFilterBinding
import com.example.cocktails.presentation.fragments.filter.recycler.adapter.FilterAdapter


class FilterFragment : Fragment(R.layout.fragment_filter) {

    private var _binding: FragmentFilterBinding? = null

    private val args: FilterFragmentArgs by navArgs()
    private val binding get() = _binding!!
    private lateinit var adapter: FilterAdapter
    private val names = arrayListOf<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createList()
        adapter = FilterAdapter(requireActivity(), names)
        binding.listItem.adapter = adapter

        initUI()
    }

    //prebaci u const
    private fun createList() {
        names.clear()
        names.add("Alcoholic or not")
        names.add("Category")
        names.add("Glass used")
        names.add("Ingredient")
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