package com.example.cocktails.presentation.fragments.specific

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocktails.R
import com.example.cocktails.data.models.Filter
import com.example.cocktails.data.models.Resource
import com.example.cocktails.databinding.FragmentSpecificfilterBinding
import com.example.cocktails.presentation.fragments.specific.recycler.adapter.SpecificFilterAdapter
import com.example.cocktails.presentation.fragments.specific.viewmodel.SpecificFilterViewModel

class SpecificFilterFragment : Fragment(R.layout.fragment_specificfilter) {


    private var _binding: FragmentSpecificfilterBinding? = null

    private val args: SpecificFilterFragmentArgs by navArgs()
    private val binding get() = _binding!!
    private val specificFilterViewModel: SpecificFilterViewModel by hiltNavGraphViewModels(R.id.specificFilterFragment)
    private lateinit var adapter: SpecificFilterAdapter
    private var filterBy: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSpecificfilterBinding.inflate(inflater, container, false)
        filterBy = args.argFilter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        initObservers()

    }

    private fun setupRecyclerView() {
        binding.rvFilter.layoutManager = LinearLayoutManager(context)
        adapter = SpecificFilterAdapter()
        adapter.onItemClickListener = { filter: Filter, _: Int ->
            val args = bundleOf("argSpec" to filter.getLabel())
            findNavController().navigate(
                R.id.action_specificFilterFragment_to_cocktailsFragment,
                args
            )

        }
        binding.rvFilter.adapter = adapter
    }

    private fun initObservers() {
        specificFilterViewModel.cocktails.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    when (filterBy) {
                        "a" -> {
                            binding.filterLabel.text = "Filter cocktails by Acoholic or not"
                        }

                        "c" -> {

                        }

                        "g" -> {

                        }

                        "i" -> {

                        }
                    }
                    val filterList = resource.data
                    adapter.submitList(filterList)
                }

                else -> {}
            }

        }
        specificFilterViewModel.fetchCocktailsByCategory(filterBy)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}