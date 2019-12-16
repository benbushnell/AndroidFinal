package hu.ait.androidfinal.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.ait.androidfinal.R
import hu.ait.androidfinal.data.Ingredient
import hu.ait.androidfinal.data.Meal
import hu.ait.androidfinal.data.PantryRepository
import hu.ait.androidfinal.fragments.RecipeViewModel
import kotlinx.android.synthetic.main.pantry_list_item.view.*
import kotlinx.android.synthetic.main.recipe_list_item.view.*

class PantryAdapter(context: Context) : RecyclerView.Adapter<PantryAdapter.ViewHolder>() {
    val context = context
    var pantryList = mutableListOf<Ingredient>()
    val viewModel = RecipeViewModel()
    val checkedList = mutableListOf<Ingredient>()
    val pantryRepository = PantryRepository()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PantryAdapter.ViewHolder {
        val ingredientCard = LayoutInflater.from(context).inflate(
            R.layout.pantry_list_item, parent, false
        )

        return ViewHolder(ingredientCard)
    }

    override fun getItemCount(): Int {
        return pantryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pantryItem = pantryList[position]

        holder.tvPantryIngredient.text = pantryItem.name
        holder.cbInclude.isChecked = pantryItem.include


        holder.cbInclude.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                pantryItem.include = true
                checkedList.add(pantryItem)
                viewModel.updateIsChecked(pantryItem)
            }else{
                pantryItem.include = false
                checkedList.remove(pantryItem)
                viewModel.updateIsChecked(pantryItem)
            }
        }

        holder.icDelete.setOnClickListener{
            pantryList.remove(pantryItem)
            notifyDataSetChanged()
            pantryRepository.deletePantryItem(pantryItem)
        }

    }

    fun addItem(item: Ingredient){
        var existing = false
        for(i in 0 until pantryList.size-1){
            if (item.name == pantryList[i].name){
                existing = true
            }
        }
        if (!existing) {
            pantryList.add(item)
            notifyItemInserted(pantryList.lastIndex)
        }
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvPantryIngredient = itemView.tvPantryIngredient
        val cbInclude = itemView.cbInclude
        val icDelete = itemView.icDelete
    }
}