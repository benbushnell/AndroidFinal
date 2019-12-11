package hu.ait.androidfinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.squareup.picasso.Picasso
import hu.ait.androidfinal.data.Base
import hu.ait.androidfinal.data.Meal
import kotlinx.android.synthetic.main.activity_recipe_details.*
import kotlinx.android.synthetic.main.recipe_list_item.*

class RecipeDetails : AppCompatActivity() {

    private lateinit var recipe : Meal
    private var instructionsList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_details)

        recipe = intent.extras?.get("Recipe") as Meal

        tvName.setText(recipe.strMeal)

        Picasso.get().load(recipe.strMealThumb).into(imgRecipeDetails)
    }

    fun instructionsList(){
        //if()
            //reflection, but its hard. Just iterate down manually

    }
}
