package com.example.cocktails.presentation.fragments.details

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.cocktails.R
import com.example.cocktails.data.models.Resource
import com.example.cocktails.databinding.FragmentCocktailDetailsBinding
import com.example.cocktails.presentation.fragments.details.viewmodel.CocktailDetailsViewModel

class CocktailDetailsFragment : Fragment(R.layout.fragment_cocktail_details) {
    private val args: CocktailDetailsFragmentArgs by navArgs()

    private var _binding: FragmentCocktailDetailsBinding? = null
    private val binding get() = _binding!!
    private val cocktailDetailsViewModel: CocktailDetailsViewModel by hiltNavGraphViewModels(R.id.cocktailDetailsFragment)
    private var argId: Int = 1
    private var argFavorite = false
    private var argEmail: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        argId = args.argDetails
        argFavorite = args.argsFavorite
        argEmail = args.argEmail
        _binding = FragmentCocktailDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    private fun initObservers() {
        cocktailDetailsViewModel.cocktailsDetails.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    val cocktail = resource.data
                    binding.progressBar.visibility = View.GONE
                    initUI(cocktail)

                }

                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE

                    val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                    builder.setMessage(resources.getString(R.string.filter_error, resource.message))
                        .setTitle(resources.getString(R.string.error))
                        .setPositiveButton(resources.getString(R.string.ok)) { _, _ ->
                        }

                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                }
            }
        }
        cocktailDetailsViewModel.fetchAllCocktailDetails(argId)
    }


    private fun initUI(cocktail: CocktailDetailsViewModel.CocktailDetailsData) {
        Glide.with(binding.cocktailImage.context)
            .load(cocktail.image)
            .into(binding.cocktailImage)

        binding.cocktailName.text = cocktail.name
        binding.labelAlcoholic.text = cocktail.alcoholic
        binding.labelCategory.text = cocktail.category
        binding.labelGlass.text = cocktail.glass
        binding.ingredientsItems.text = cocktail.ingredients
        binding.instructionsItems.text = cocktail.instruction
        setImage()

        binding.favoriteButton.setOnClickListener {
            if (argFavorite) {
                cocktailDetailsViewModel.deleteCocktail(
                    cocktailDetailsViewModel.createCocktail(
                        cocktail,
                        argId,
                        argFavorite,
                        argEmail
                    )
                )
            } else {
                cocktailDetailsViewModel.insertCocktail(
                    cocktailDetailsViewModel.createCocktail(
                        cocktail,
                        argId,
                        argFavorite,
                        argEmail
                    )
                )
            }
            argFavorite = !argFavorite
            setImage()
        }
    }

    private fun setImage() {
        val imageResourceId = if (argFavorite) {
            R.drawable.imageview_checked
        } else {
            R.drawable.imageview_unchecked
        }
        Glide.with(binding.favoriteButton.context).load(imageResourceId)
            .into(binding.favoriteButton)
    }

}