package hu.ait.androidfinal.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.ait.androidfinal.R
import kotlinx.android.synthetic.main.ingredient_step.view.*

class RecipeDetailsAdapter(context: Context, listIngredients: List<Pair<String, String>>): RecyclerView.Adapter<RecipeDetailsAdapter.ViewHolder>() {

    var ingredientList = listIngredients
    val context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val ingredient = LayoutInflater.from(context).inflate(
            R.layout.ingredient_step, parent, false
        )

        Log.d("list", ingredientList.toString() + "OnCreate")

        return ViewHolder(ingredient)
    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingredient = ingredientList[position].first
        val amount = ingredientList[position].second
        Log.d("list", ingredientList.toString() + "onbind")

        holder.tvIngredient.text = amount + " " +ingredient
    }

    //fun addIngredientPairing(ingredient: String, amount: String) {
    //    ingredientList.add(Pair(ingredient, amount))
    //    notifyItemInserted(ingredientList.lastIndex)
   // }

    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val tvIngredient = itemview.tvIngredient
    }

}