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
import com.example.cocktails.data.models.Cocktail
import com.example.cocktails.data.models.CocktailDetails
import com.example.cocktails.data.models.Resource
import com.example.cocktails.databinding.FragmentCocktailDetailsBinding
import com.example.cocktails.presentation.fragments.details.viewmodel.CocktailDetailsViewModel

class CocktailDetailsFragment : Fragment(R.layout.fragment_cocktail_details) {
    private val args: CocktailDetailsFragmentArgs by navArgs()

    private var _binding: FragmentCocktailDetailsBinding? = null
    private var cocktailDetailsMap: Map<String, String>? = mapOf()
    private val binding get() = _binding!!
    private val cocktailDetailsViewModel: CocktailDetailsViewModel by hiltNavGraphViewModels(R.id.cocktailDetailsFragment)
    private var argId: Int = 1
    private var argFavorite = false
    private var argEmail: String = ""
    private var cocktailList: List<Cocktail> = mutableListOf()

    private val stringBuilderIngredient = StringBuilder()


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
                    binding.progressBar.visibility = View.GONE
                    val cocktailDetails = resource.data
                    cocktailDetailsMap = getNonNullFields(cocktailDetails)
                    createList(cocktailDetails)
                    setDetails()
                    initUI()
                }

                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE

                    val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                    builder.setMessage(resources.getString(R.string.filter_error, resource.message))
                        .setTitle("Error")
                        .setPositiveButton("OK") { _, _ ->
                        }

                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                }
            }
        }

        cocktailDetailsViewModel.fetchAllCocktailDetails(argId)

    }

    private fun createList(cocktailDetails: List<CocktailDetails>) {
        cocktailList = cocktailDetails.map { cocktailDetailsItem ->
            Cocktail(
                id = cocktailDetailsItem.idDrink,
                name = cocktailDetailsItem.strDrink,
                image = cocktailDetailsItem.strDrinkThumb,
                alcoholic = cocktailDetailsItem.strAlcoholic,
                favorite = argFavorite,
                email = argEmail
            )
        }
    }

    private fun initUI() {
        Glide.with(binding.cocktailImage.context)
            .load(cocktailDetailsMap?.get("strDrinkThumb"))
            .into(binding.cocktailImage)

        binding.cocktailName.text = cocktailDetailsMap?.get("strDrink")
        binding.labelAlcoholic.text = cocktailDetailsMap?.get("strAlcoholic")
        binding.labelCategory.text = cocktailDetailsMap?.get("strCategory")
        binding.labelGlass.text = cocktailDetailsMap?.get("strGlass")
        binding.ingredientsItems.text = stringBuilderIngredient
        binding.instructionsItems.text = cocktailDetailsMap?.get("strInstructions")
        setImage()

        binding.favoriteButton.setOnClickListener {
            if (argFavorite) {
                cocktailDetailsViewModel.deleteCocktail(cocktailList.first())
            } else {
                cocktailDetailsViewModel.insertCocktail(cocktailList.first())
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

    private fun getNonNullFields(cocktailDetailsList: List<CocktailDetails>): Map<String, String>? {
        return cocktailDetailsList.firstOrNull()?.let { cocktailDetails ->
            val fields = CocktailDetails::class.java.declaredFields
            val result = mutableMapOf<String, String>()

            for (field in fields) {
                field.isAccessible = true
                val value = field.get(cocktailDetails)
                if (value != null) {
                    result[field.name] = value.toString()
                }
            }

            result.ifEmpty { null }
        }
    }

    private fun setDetails() {
        for (i in 1..15) {
            val ingredientKey = "strIngredient$i"

            val ingredient = cocktailDetailsMap?.get(ingredientKey)

            if (!ingredient.isNullOrEmpty()) {
                stringBuilderIngredient.append("$ingredient\n")
            } else {
                break
            }
        }
    }

}