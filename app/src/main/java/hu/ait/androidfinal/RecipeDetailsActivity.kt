package hu.ait.androidfinal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.squareup.picasso.Picasso
import hu.ait.androidfinal.adapter.RecipeDetailsAdapter
import hu.ait.androidfinal.data.Meal
import hu.ait.androidfinal.data.RecipeAPIRepo
import hu.ait.androidfinal.fragments.RecipeViewModel
import kotlinx.android.synthetic.main.activity_recipe_details.*


class RecipeDetailsActivity : AppCompatActivity(){

    private var ingredientList = mutableListOf<String>()
    private var amountList = mutableListOf<String>()
    private lateinit var recipeShell: Meal
    private lateinit var recipe: Meal
    var favorited = true
    val recipeApiRepo = RecipeAPIRepo()
    private lateinit var viewModel: RecipeViewModel

    lateinit var recipeDetailsAdapter: RecipeDetailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_details)



        recipe = (intent.getSerializableExtra("meal") as Meal)
        favorited = (intent.getSerializableExtra("fav") as Boolean)

        viewModel = ViewModelProviders.of(this).get(RecipeViewModel::class.java)
        tvDetailsName.text = recipe.strMeal
        tvDirections.text = recipe.strInstructions
        Picasso.get().load(recipe.strMealThumb).into(imgRecipeDetails)
        viewModel.getSavedFavorites()
            .observe(this, Observer { savedFavorites -> inFavorites(savedFavorites) })
        initRecycler()

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

    private fun initRecycler () {
        recipeDetailsAdapter = RecipeDetailsAdapter(
            this@RecipeDetailsActivity,
            viewModel.instructionsList(recipe, ingredientList, amountList)
        )
        recyclerIngredients.adapter = recipeDetailsAdapter
        recyclerIngredients.layoutManager = GridLayoutManager(this@RecipeDetailsActivity, 2)
    }


    private fun inFavorites (favList: List<Meal>){
        for(i in 0 until (favList.size-1)){
            if(recipe.idMeal == favList[i].idMeal){
                favorited = true
            } else{
                favorited = false
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
