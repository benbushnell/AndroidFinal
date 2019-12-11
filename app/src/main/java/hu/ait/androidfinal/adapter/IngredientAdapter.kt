package hu.ait.androidfinal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.ait.androidfinal.R
import kotlinx.android.synthetic.main.ingredient_step.view.*

class IngredientAdapter: RecyclerView.Adapter<IngredientAdapter.ViewHolder> {

    var ingredientsList = mutableListOf<String>()
    var context: Context


    constructor(context: Context, listIngredients: List<String>) {
        this.context = context

        ingredientsList.addAll(listIngredients)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val ingredient = LayoutInflater.from(context).inflate(
            R.layout.ingredient_step, parent, false
        )

        return ViewHolder(ingredient)
    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingredient = ingredientsList[position]

        holder.tvAmount.text = ingredient
    }

    fun addIngredient(ingredient: String) {
        ingredientsList.add(ingredient)
        notifyItemInserted(ingredientsList.lastIndex)
    }

    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val tvAmount = itemview.tvAmount
        val tvIngredient = itemview.tvIngredient
    }
}