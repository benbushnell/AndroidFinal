package hu.ait.androidfinal.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hu.ait.androidfinal.MainActivity
import hu.ait.androidfinal.R
import hu.ait.androidfinal.data.Meal
import hu.ait.androidfinal.fragments.RecipeDetailsFragment
import kotlinx.android.synthetic.main.recipe_list_item.view.*
import java.io.Serializable


class FavoritesAdapter(context: Context) : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {
    val context = context
    var recipesList = listOf<Meal>()
    val bundle = Bundle()
    var opened = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesAdapter.ViewHolder {
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
            bundle.putSerializable("meal", recipeItem as Serializable)
            (context as MainActivity).showFragmentByTag(RecipeDetailsFragment.TAG, true, bundle)
        }


        Picasso.get().load(recipeItem.strMealThumb).into(holder.imgRecipe)
    }

    //fun addRecipe(recipe: Meal){
    //   recipesList.add(recipe)
    //    notifyItemInserted(recipesList.lastIndex)
    //}

    fun replaceItems(recipes: List<Meal>) {
        this.recipesList = recipes
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvRecipeName = itemView.tvRecipeName
        val wholeCard = itemView.wholeCard
        val imgRecipe = itemView.imgRecipe
    }

}