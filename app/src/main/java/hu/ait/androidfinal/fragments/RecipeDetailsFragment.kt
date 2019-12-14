package hu.ait.androidfinal.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.squareup.picasso.Picasso
import hu.ait.androidfinal.R
import hu.ait.androidfinal.adapter.FavoritesAdapter
import hu.ait.androidfinal.adapter.RecipeDetailsAdapter
import hu.ait.androidfinal.data.Meal
import kotlinx.android.synthetic.main.activity_recipe_details.*
import kotlinx.android.synthetic.main.activity_recipe_details.view.*
import android.R.attr.data



class RecipeDetailsFragment : Fragment(){

    private lateinit var recipe : Meal
    private lateinit var bundle: Bundle
    private var ingredientList = mutableListOf<String>()
    private var amountList = mutableListOf<String>()
    var populated = false

    companion object{
        fun newInstance() = RecipeDetailsFragment
        const val TAG = "RecipeDetailsFragment"
    }

    private lateinit var viewModel: RecipeViewModel

    lateinit var recipeDetailsAdapter: RecipeDetailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("Call", "onCreate called")
        super.onCreate(savedInstanceState)
        bundle = getArguments()!!
        recipe = bundle.getSerializable("meal") as Meal
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.activity_recipe_details, container, false)
        rootView.tvDetailsName.text = recipe.strMeal
        Picasso.get().load(recipe.strMealThumb).into(rootView.imgRecipeDetails)
        Log.d("Call", "onCreateView called")
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("Call", "onActivityCreated called")
        viewModel = ViewModelProviders.of(this).get(RecipeViewModel::class.java)
        tvDirections.text = recipe.strInstructions

        recipeDetailsAdapter = RecipeDetailsAdapter(activity!!, viewModel.instructionsList(recipe,ingredientList, amountList))
        recyclerIngredients.adapter = recipeDetailsAdapter
        recyclerIngredients.layoutManager = GridLayoutManager(activity, 2)

        //for ()
        ivFavicon.setOnClickListener{
        }
    }
}