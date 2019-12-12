package hu.ait.androidfinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import hu.ait.androidfinal.adapter.PantryAdapter
import hu.ait.androidfinal.adapter.RecipesPagerAdapter
import hu.ait.androidfinal.data.Ingredient
import hu.ait.androidfinal.fragments.NewPantryItemDialog
import hu.ait.androidfinal.fragments.RecipeViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NewPantryItemDialog.ItemHandler {

    //https://www.themealdb.com/api/json/v1/1/search.php?s=Arrabiata

    private lateinit var viewModel: RecipeViewModel
    lateinit var pantryAdapter: PantryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(RecipeViewModel::class.java)
        pantryAdapter = PantryAdapter(this)
        viewpager.adapter = RecipesPagerAdapter(supportFragmentManager)


    }

    override fun itemCreated(pantryItem: Ingredient) {
        viewModel.saveItemToPantry(pantryItem)
        pantryAdapter.addItem(pantryItem)
    }

    override fun itemUpdated(pantryItem: Ingredient) {
        //viewModel.
    }

}
