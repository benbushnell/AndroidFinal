package hu.ait.androidfinal.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hu.ait.androidfinal.MainActivity
import hu.ait.androidfinal.R
import hu.ait.androidfinal.RecipeDetailsActivity
import hu.ait.androidfinal.data.Meal
import hu.ait.androidfinal.fragments.RecipeDetailsFragment
import kotlinx.android.synthetic.main.recipe_list_item.view.*
import java.io.Serializable


class FavoritesAdapter(context: Context) : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {
    val context = context
    var recipesList = mutableListOf<Meal>()
    val bundle = Bundle()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
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

        holder.tvRecipeName.text = recipeItem.strMeal
        holder.wholeCard.setOnClickListener {
            val intent = Intent()
            intent.setClass(context as MainActivity, RecipeDetailsActivity::class.java )
            intent.putExtra("meal", recipeItem)
            intent.putExtra("fav", true)
            context.startActivity(intent)

        }


        Picasso.get().load(recipeItem.strMealThumb).into(holder.imgRecipe)
    }

    fun addRecipe(recipe: Meal){
        recipesList.add(recipe)
        notifyItemInserted(recipesList.lastIndex)
    }

    fun removeIngredientByName(recipe: Meal) {
        val index = recipesList.indexOf(recipe)
        if (index != -1) {
            recipesList.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvRecipeName = itemView.tvRecipeName
        val wholeCard = itemView.wholeCard
        val imgRecipe = itemView.imgRecipe
    }

}