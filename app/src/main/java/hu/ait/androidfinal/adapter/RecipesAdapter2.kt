package hu.ait.androidfinal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hu.ait.androidfinal.MainActivity
import hu.ait.androidfinal.R
import hu.ait.androidfinal.data.Meal
import kotlinx.android.synthetic.main.recipe_list_item.view.*

class RecipesAdapter2(val listRecipes : List<Meal> ) :RecyclerView.Adapter<RecipesAdapter2.RecipeViewHolder>() {

    val recipesList = mutableListOf<Meal>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val recipeItemCard = LayoutInflater.from(parent.context).inflate(
            R.layout.recipe_list_item, parent, false
        )

        recipesList.addAll(listRecipes)

        return RecipeViewHolder(recipeItemCard)
    }

    override fun getItemCount(): Int {
        return recipesList.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipeItem = recipesList[position]

        holder.tvRecipeName.text = recipeItem.strMeal

        holder.wholeCard.setOnClickListener {
        }

        Picasso.get().load(recipesList[position].strMealThumb).into(holder.imgRecipe)
    }

    fun addRecipe(recipe: Meal){
        recipesList.add(recipe)
        notifyItemInserted(recipesList.lastIndex)
    }


    class RecipeViewHolder(val view : View) : RecyclerView.ViewHolder(view){
        val tvRecipeName = itemView.tvRecipeName
        val wholeCard = itemView.wholeCard
        val imgRecipe = itemView.imgRecipe
    }
}