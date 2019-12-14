package hu.ait.androidfinal.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.squareup.picasso.Picasso
import hu.ait.androidfinal.R
import hu.ait.androidfinal.adapter.RecipeDetailsAdapter
import hu.ait.androidfinal.data.Meal
import kotlinx.android.synthetic.main.activity_recipe_details.*
import kotlinx.android.synthetic.main.activity_recipe_details.view.*
import android.text.method.ScrollingMovementMethod
import androidx.lifecycle.Observer


class RecipeDetailsFragment : Fragment(){

    private lateinit var recipe : Meal
    private lateinit var bundle: Bundle
    private var ingredientList = mutableListOf<String>()
    private var amountList = mutableListOf<String>()
    var favorited = false

    companion object{
        const val TAG = "RecipeDetailsFragment"
    }

    private lateinit var viewModel: RecipeViewModel

    lateinit var recipeDetailsAdapter: RecipeDetailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RecipeViewModel::class.java)
        viewModel.getSavedFavorites()
            .observe(this, Observer { savedFavorites -> inFavorites(savedFavorites) })
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
        rootView.tvDirections.setMovementMethod(ScrollingMovementMethod())
        rootView.tvDirections.text = recipe.strInstructions
        viewModel.getSavedFavorites()
            .observe(this, Observer { savedFavorites -> inFavorites(savedFavorites) })
        Log.d("text", rootView.tvDirections.text.toString())
        Picasso.get().load(recipe.strMealThumb).into(rootView.imgRecipeDetails)
        Log.d("Call", "onCreateView called")
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("Call", "onActivityCreated called")
        //viewModel.getSavedFavorites().observe(this, Observer {savedFavorites -> inFavorites(savedFavorites)})


        recipeDetailsAdapter = RecipeDetailsAdapter(activity!!, viewModel.instructionsList(recipe,ingredientList, amountList))
        recyclerIngredients.adapter = recipeDetailsAdapter
        recyclerIngredients.layoutManager = GridLayoutManager(activity, 2)

        ivFavicon.setOnClickListener {
            if (favorited){
                viewModel.deleteFavorite(recipe)
                favorited = false
            } else {
                viewModel.saveFavoriteToRepo(recipe)
                favorited = true
            }
            changeFavIcon()
        }
    }
    /**
    override fun onStop() {
        Log.d("stopping", "called")
        fragmentManager!!.popBackStack()
        super.onStop()
    }
    **/


    private fun inFavorites (favList: List<Meal>){
        for(i in 0 until (favList.size-1)){
            if(recipe.idMeal == favList[i].idMeal){
                favorited = true
            }
        }
        changeFavIcon()
    }

    private fun changeFavIcon() {
        if (favorited) {
            ivFavicon.setChecked(true)
            ivFavicon.playAnimation()
        } else {
            ivFavicon.setChecked(false)
            ivFavicon.playAnimation()
        }
    }
}