package com.example.cocktails.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cocktails.R
import com.example.cocktails.data.models.Cocktail
import com.example.cocktails.databinding.FragmentCocktailsBinding
import com.example.cocktails.presentation.view.recycler.adapter.CocktailAdapter


class CocktailsFragment : Fragment(R.layout.fragment_cocktails) {


    private var _binding: FragmentCocktailsBinding? = null
    private val binding get() = _binding!!


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
                TODO("What should I do when I click?")
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)


        val coctailsFragment = mutableListOf(
            Cocktail(
                1,
                "Cocktail1",
                R.drawable.photo,
                false
            ), Cocktail(
                2,
                "Cocktail2",
                R.drawable.photo2,
                false
            ), Cocktail(
                3,
                "Cocktail3",
                R.drawable.photo3,
                false
            ), Cocktail(
                4,
                "Cocktail4",
                R.drawable.photo4,
                false
            ), Cocktail(
                5,
                "Cocktail5",
                R.drawable.photo5,
                false
            ), Cocktail(
                6,
                "Cocktail6",
                R.drawable.photo6,
                false
            )
        )
        binding.rvCocktails.layoutManager = GridLayoutManager(context, 2)
        val adapter = CocktailAdapter(coctailsFragment)
        binding.rvCocktails.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}