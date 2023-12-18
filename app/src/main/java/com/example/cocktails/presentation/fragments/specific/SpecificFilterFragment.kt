package com.example.cocktails.presentation.fragments.specific

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        val recyclerView: RecyclerView = binding.rvFilter
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        val dividerItemDecoration =
            DividerItemDecoration(recyclerView.context,layoutManager.orientation)

        recyclerView.addItemDecoration(dividerItemDecoration)

        adapter = SpecificFilterAdapter()
        adapter.onItemClickListener = { filter: Filter, _: Int ->
            val args = bundleOf("argSpec" to filter.getLabel(), "filterBy" to filterBy)
            findNavController().navigate(
                R.id.action_specificFilterFragment_to_cocktailsFragment, args
            )

        }
        recyclerView.adapter = adapter
    }

    private fun initObservers() {
        specificFilterViewModel.cocktails.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    val filterList = resource.data

                    binding.rvFilter.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE

                    when (filterBy) {
                        "a" -> {
                            setLabel(R.string.filter_alcoholic)
                        }

                        "c" -> {
                            setLabel(R.string.filter_category)
                        }

                        "g" -> {
                            setLabel(R.string.filter_glass)
                        }

                        "i" -> {
                            setLabel(R.string.filter_ingredient)

                        }
                    }

                    adapter.submitList(filterList)

                }

                is Resource.Loading -> {
                    binding.rvFilter.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvFilter.visibility = View.GONE

                    val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                    builder.setMessage(resources.getString(R.string.filter_error, resource.message)).setTitle("Error")
                        .setPositiveButton("OK") { _, _ ->
                        }

                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                }
            }
        }
        specificFilterViewModel.fetchCocktailsByCategory(filterBy)
    }

    private fun setLabel(filterResource: Int) {
        val filterLabel = binding.filterLabel
        filterLabel.text = resources.getString(filterResource)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}