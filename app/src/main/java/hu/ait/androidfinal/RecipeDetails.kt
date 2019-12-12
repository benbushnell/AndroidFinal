package hu.ait.androidfinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.squareup.picasso.Picasso
import hu.ait.androidfinal.data.Meal
import kotlinx.android.synthetic.main.activity_recipe_details.*

class RecipeDetails : AppCompatActivity() {

    private lateinit var recipe : Meal
    private var ingredientList = mutableListOf<String>()
    private var amountList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_details)

        recipe = intent.extras?.get("Recipe") as Meal

        tvName.setText(recipe.strMeal)

        Picasso.get().load(recipe.strMealThumb).into(imgRecipeDetails)
        instructionsList()
        Log.d("list", ingredientList.toString())
        Log.d("list", amountList.toString())
    }

    fun instructionsList(){
        if(recipe.strIngredient20 != ""){
            ingredientList.add(recipe.strIngredient20!!)
            amountList.add(recipe.strMeasure20!!)
        }
        if(recipe.strIngredient1 != ""){
            ingredientList.add(recipe.strIngredient19!!)
            amountList.add(recipe.strMeasure19!!)
        }
        if(recipe.strIngredient1 != ""){
            ingredientList.add(recipe.strIngredient18!!)
            amountList.add(recipe.strMeasure18!!)
        }
        if(recipe.strIngredient1 != ""){
            ingredientList.add(recipe.strIngredient17!!)
            amountList.add(recipe.strMeasure17!!)
        }
        if(recipe.strIngredient1 != ""){
            ingredientList.add(recipe.strIngredient16!!)
            amountList.add(recipe.strMeasure16!!)
        }
        if(recipe.strIngredient1 != ""){
            ingredientList.add(recipe.strIngredient15!!)
            amountList.add(recipe.strMeasure15!!)
        }
        if(recipe.strIngredient1 != ""){
            ingredientList.add(recipe.strIngredient14!!)
            amountList.add(recipe.strMeasure14!!)
        }
        if(recipe.strIngredient1 != ""){
            ingredientList.add(recipe.strIngredient13!!)
            amountList.add(recipe.strMeasure13!!)
        }
        if(recipe.strIngredient1 != ""){
            ingredientList.add(recipe.strIngredient12!!)
            amountList.add(recipe.strMeasure12!!)
        }
        if(recipe.strIngredient1 != ""){
            ingredientList.add(recipe.strIngredient11!!)
            amountList.add(recipe.strMeasure11!!)
        }
        if(recipe.strIngredient1 != ""){
            ingredientList.add(recipe.strIngredient10!!)
            amountList.add(recipe.strMeasure10!!)
        }
        if(recipe.strIngredient1 != ""){
            ingredientList.add(recipe.strIngredient9!!)
            amountList.add(recipe.strMeasure9!!)
        }
        if(recipe.strIngredient1 != ""){
            ingredientList.add(recipe.strIngredient8!!)
            amountList.add(recipe.strMeasure8!!)
        }
        if(recipe.strIngredient1 != ""){
            ingredientList.add(recipe.strIngredient7!!)
            amountList.add(recipe.strMeasure7!!)
        }
        if(recipe.strIngredient1 != ""){
            ingredientList.add(recipe.strIngredient6!!)
            amountList.add(recipe.strMeasure6!!)
        }
        if(recipe.strIngredient1 != ""){
            ingredientList.add(recipe.strIngredient5!!)
            amountList.add(recipe.strMeasure5!!)
        }
        if(recipe.strIngredient1 != ""){
            ingredientList.add(recipe.strIngredient4!!)
            amountList.add(recipe.strMeasure4!!)
        }
        if(recipe.strIngredient1 != ""){
            ingredientList.add(recipe.strIngredient3!!)
            amountList.add(recipe.strMeasure3!!)
        }
        if(recipe.strIngredient1 != ""){
            ingredientList.add(recipe.strIngredient2!!)
            amountList.add(recipe.strMeasure2!!)
        }
            if(recipe.strIngredient1 != ""){
            ingredientList.add(recipe.strIngredient1!!)
            amountList.add(recipe.strMeasure1!!)
        }


    }
}
