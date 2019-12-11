package hu.ait.androidfinal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import hu.ait.androidfinal.MainActivity
import hu.ait.androidfinal.R
import hu.ait.androidfinal.data.Meal
import kotlinx.android.synthetic.main.recipe_list_item.view.*

class RecipesAdapter(context: Context) : RecyclerView.Adapter<RecipesAdapter.ViewHolder>() {
    val context = context
    var recipesList = mutableListOf<Meal>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesAdapter.ViewHolder {
        val recipeItemCard = LayoutInflater.from(context).inflate(
            R.layout.recipe_list_item, parent, false
        )

        val recipeList = mutableListOf<Meal>()

        return ViewHolder(recipeItemCard)
    }

    override fun getItemCount(): Int {
        return recipesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipeItem = recipesList[position]

        holder.tvRecipeName.text = recipeItem.strMeal

        holder.wholeCard.setOnClickListener {
            Toast.makeText((context as MainActivity), "Opened ${holder.tvRecipeName.text}", Toast.LENGTH_LONG).show()
        }

        Picasso.get().load(recipesList[position].strMealThumb).into(holder.imgRecipe)
    }

    fun addRecipe(recipe: Meal){
        recipesList.add(recipe)
        notifyItemInserted(recipesList.lastIndex)
    }

    fun replaceItems(recipes: MutableList<Meal>) {
        this.recipesList = recipes
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvRecipeName = itemView.tvRecipeName
        val wholeCard = itemView.wholeCard
        val imgRecipe = itemView.imgRecipe

    }

}