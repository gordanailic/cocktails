package com.example.cocktails.presentation.fragments.cocktails

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cocktails.R
import com.example.cocktails.data.models.Resource
import com.example.cocktails.databinding.FragmentCocktailsBinding
import com.example.cocktails.presentation.fragments.cocktails.recycler.adapter.CocktailAdapter
import com.example.cocktails.presentation.fragments.cocktails.viewmodel.CocktailViewModel
import timber.log.Timber

class CocktailsFragment : Fragment(R.layout.fragment_cocktails) {


    private var _binding: FragmentCocktailsBinding? = null
    private val binding get() = _binding!!
    private val cocktailViewModel: CocktailViewModel by hiltNavGraphViewModels(R.id.cocktailsFragment)
    private lateinit var adapter: CocktailAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCocktailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.my_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

                if (binding.inputEt.isVisible) {
                    binding.inputEt.visibility = View.GONE
                } else {
                    binding.inputEt.visibility = View.VISIBLE

                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        setupRecyclerView()
        initObservers()
        initListeners()
    }

    private fun initObservers() {

        cocktailViewModel.cocktails.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    val cocktailsList = resource.data
                    if (cocktailsList.isEmpty()) {
                        binding.labelNothing.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                    } else {
                        binding.rvCocktails.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        binding.labelNothing.visibility = View.GONE
                        adapter.submitList(cocktailsList)
                    }
                }

                is Resource.Loading -> {
                    binding.rvCocktails.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvCocktails.visibility = View.GONE

                    val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                    builder.setMessage("Error loading data: ${resource.message}").setTitle("Error")
                        .setPositiveButton("OK") { _, _ ->
                        }

                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                    Timber.e("Error loading data: ${resource.message}")
                }
            }
        }
        cocktailViewModel.fetchAllCocktails("")


    }

    private fun setupRecyclerView() {
        binding.rvCocktails.layoutManager = GridLayoutManager(context, 2)
        adapter = CocktailAdapter()
        binding.rvCocktails.adapter = adapter
    }

    private fun initListeners() {
        binding.inputEt.doAfterTextChanged {
            val filter = it.toString()
            cocktailViewModel.fetchAllCocktails(filter)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}