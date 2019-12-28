package hu.ait.androidfinal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.squareup.picasso.Picasso
import hu.ait.androidfinal.adapter.RecipeDetailsAdapter
import hu.ait.androidfinal.data.Meal
import kotlinx.android.synthetic.main.activity_recipe_details.*


class RecipeDetailsActivity : AppCompatActivity(){

    private var ingredientList = mutableListOf<String>()
    private var amountList = mutableListOf<String>()
    private lateinit var recipe: Meal
    private lateinit var viewModel: RecipeViewModel
    private var favList = listOf<Meal>()

    lateinit var recipeDetailsAdapter: RecipeDetailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_details)

        recipe = (intent.getSerializableExtra("meal") as Meal)

        viewModel = ViewModelProviders.of(this).get(RecipeViewModel::class.java)
        tvDetailsName.text = recipe.strMeal
        tvDirections.text = recipe.strInstructions
        Picasso.get().load(recipe.strMealThumb).into(imgRecipeDetails)
        viewModel.getSavedFavorites()
            .observe(this, Observer { savedFavorites -> favList = savedFavorites
                if (recipe in favList){
                    ivFavicon.isChecked = true
                }
            })
        initRecycler()

        ivFavicon.setOnClickListener {
            if (recipe in favList){
                viewModel.deleteFavorite(recipe)
                ivFavicon.isChecked = false
            } else {
                ivFavicon.playAnimation()
                viewModel.saveFavoriteToRepo(recipe)
            }
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

}
