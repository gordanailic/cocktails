package com.example.cocktails.presentation.view.fragments.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cocktails.R
import com.example.cocktails.data.models.FavoriteRecyclerViewItem
import com.example.cocktails.databinding.FragmentFavoritesBinding
import com.example.cocktails.presentation.view.fragments.favorites.recycler.adapter.FavoriteRecyclerViewAdapter
import com.example.cocktails.presentation.view.fragments.favorites.recycler.adapter.FavoriteRecyclerViewAdapter.Companion.ALCOHOLIC_VIEW
import com.example.cocktails.presentation.view.fragments.favorites.recycler.adapter.FavoriteRecyclerViewAdapter.Companion.FAVORITE_VIEW


class FavoritesFragment : Fragment(R.layout.fragment_favorites) {


    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FavoriteRecyclerViewAdapter

    private val items: MutableList<FavoriteRecyclerViewItem> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeItems()
        setupRecyclerView()
    }

    private fun initializeItems() {
        items.add(FavoriteRecyclerViewItem.Alcoholic("Alcoholic"))
        items.add(
            FavoriteRecyclerViewItem.Favorite(
                1,
                "Koktel1",
                "https://images.unsplash.com/photo-1601887573188-79fb3c767157?q=80&w=2030&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "Item 1"
            )
        )
        items.add(
            FavoriteRecyclerViewItem.Favorite(
                2,
                "Koktel2",
                "https://images.unsplash.com/photo-1609951651556-5334e2706168?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "Item 2"
            )
        )
        items.add(
            FavoriteRecyclerViewItem.Favorite(
                3,
                "Koktel3",
                "https://images.unsplash.com/photo-1607622750671-6cd9a99eabd1?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "Item 3"
            )
        )
        items.add(FavoriteRecyclerViewItem.Alcoholic("Non-Alcoholic"))
        items.add(
            FavoriteRecyclerViewItem.Favorite(
                4,
                "Koktel4",
                "https://images.unsplash.com/photo-1563223771-5fe4038fbfc9?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "Item 4"
            )
        )
        items.add(
            FavoriteRecyclerViewItem.Favorite(
                5,
                "Koktel5",
                "https://images.unsplash.com/photo-1583898350903-99fa829dad3d?q=80&w=2030&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "Item 5"
            )
        )
        items.add(
            FavoriteRecyclerViewItem.Favorite(
                6,
                "Koktel6",
                "https://images.unsplash.com/photo-1572590016064-3e6ae9c04947?q=80&w=2127&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "Item 6"
            )
        )

        items.add(
            FavoriteRecyclerViewItem.Favorite(
                7,
                "Koktel7",
                "https://images.unsplash.com/photo-1618799805265-4f27cb61ede9?q=80&w=1935&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "Item 7"
            )
        )
    }

    private fun setupRecyclerView() {
        val layoutManager = GridLayoutManager(activity, 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (adapter.getItemViewType(position)) {
                    ALCOHOLIC_VIEW -> 2
                    FAVORITE_VIEW -> 1
                    else -> throw IllegalArgumentException("Invalid ViewType Provided")
                }
            }
        }
        binding.rvFavorites.layoutManager = layoutManager
        adapter = FavoriteRecyclerViewAdapter()
        adapter.submitList(items)
        binding.rvFavorites.adapter = adapter

    }
}