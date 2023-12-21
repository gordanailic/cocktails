package com.example.cocktails.presentation.fragments.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cocktails.Constants
import com.example.cocktails.R
import com.example.cocktails.data.models.FavoriteRecyclerViewItem
import com.example.cocktails.databinding.FragmentFavoritesBinding
import com.example.cocktails.presentation.fragments.favorites.recycler.adapter.FavoriteRecyclerViewAdapter
import com.example.cocktails.presentation.fragments.favorites.viewmodel.FavoritesViewModel


class FavoritesFragment : Fragment(R.layout.fragment_favorites) {


    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FavoriteRecyclerViewAdapter
    private val favoritesViewModel: FavoritesViewModel by hiltNavGraphViewModels(R.id.favoritesFragment)
    private lateinit var email: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val intent = requireActivity().intent
        email = intent.getStringExtra(Constants.emailKey).toString()
        favoritesViewModel.getFavorites(email)
        setupRecyclerView()
        initObservers()
    }


    private fun setupRecyclerView() {
        val layoutManager = GridLayoutManager(activity, 2)
        binding.rvFavorites.layoutManager = layoutManager
        adapter = FavoriteRecyclerViewAdapter()
        adapter.onItemClick = { cocktail: FavoriteRecyclerViewItem.Favorite, _: Int ->
            val args = bundleOf(
                "argDetails" to cocktail.id,
                "argsFavorite" to true,
                "argEmail" to email
            )
            findNavController().navigate(
                R.id.action_favoritesFragment_to_cocktailDetailsFragment,
                args
            )

        }
        binding.rvFavorites.adapter = adapter
    }

    private fun initObservers() {
        favoritesViewModel.favoritesList.observe(viewLifecycleOwner) { resource ->
            if (resource.isEmpty()) {
                binding.labelNothing.visibility = View.VISIBLE
                binding.rvFavorites.visibility = View.GONE
            } else {
                binding.labelNothing.visibility = View.GONE
                binding.rvFavorites.visibility = View.VISIBLE
                adapter.submitList(resource)

            }
        }
    }
}