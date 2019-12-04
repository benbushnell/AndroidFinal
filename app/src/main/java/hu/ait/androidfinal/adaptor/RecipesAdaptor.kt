package hu.ait.androidfinal.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import hu.ait.androidfinal.MainActivity
import hu.ait.androidfinal.R
import hu.ait.androidfinal.data.Recipe
import kotlinx.android.synthetic.main.recipe_list_item.view.*

class RecipesAdaptor : RecyclerView.Adapter<RecipesAdaptor.ViewHolder> {

    var recipesList = mutableListOf<Recipe>()
    var context : Context

    constructor(context: Context, listRecipes: List<Recipe>){
        this.context = context

        recipesList.addAll(listRecipes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesAdaptor.ViewHolder {
        val recipeItemCard = LayoutInflater.from(context).inflate(
            R.layout.recipe_list_item, parent, false
        )

        return ViewHolder(recipeItemCard)
    }

    override fun getItemCount(): Int {
        return recipesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipeItem = recipesList[position]

        holder.tvRecipeName.text = recipeItem.extendedIngredients?.get(0)?.name

        holder.wholeCard.setOnClickListener {
            Toast.makeText((context as MainActivity), "Opened ${holder.tvRecipeName.text}", Toast.LENGTH_LONG).show()
        }
    }

    fun addRecipe(recipe: Recipe){
        recipesList.add(recipe)
        notifyItemInserted(recipesList.lastIndex)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvRecipeName = itemView.tvRecipeName
        val wholeCard = itemView.wholeCard

    }

}