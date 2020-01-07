package hu.ait.androidfinal.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hu.ait.androidfinal.R
import hu.ait.androidfinal.RecipeDetailsActivity
import hu.ait.androidfinal.SearchResultsActivity
import hu.ait.androidfinal.data.Meal
import hu.ait.androidfinal.data.RecipeAPIRepo
import kotlinx.android.synthetic.main.recipe_list_item.view.*

class SearchResultsAdapter(context: Context) : RecyclerView.Adapter<SearchResultsAdapter.ViewHolder>() {
    val context = context
    private var recipesList = mutableListOf<Meal>()
    private val recipeApiRepo = RecipeAPIRepo()


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

        Picasso.get().load(recipeItem.strMealThumb).into(holder.imgRecipe)

        holder.wholeCard.setOnClickListener {
            recipeApiRepo.getRecipeById(
                recipeItem.idMeal!!
            ).observe((context as SearchResultsActivity), Observer { base ->
                val list = base.meals
                val recipe = list[0]
                val intent = Intent()
                intent.setClass(context, RecipeDetailsActivity::class.java)
                intent.putExtra("meal", recipe)
                intent.putExtra("fav", false)
                context.startActivity(intent)
            })
        }
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