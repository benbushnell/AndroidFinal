package hu.ait.androidfinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import hu.ait.androidfinal.adapter.RecipesPagerAdapter
import hu.ait.androidfinal.data.Ingredient
import hu.ait.androidfinal.fragments.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NewPantryItemDialog.ItemHandler {

    private lateinit var viewModel: RecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(RecipeViewModel::class.java)
        viewpager.adapter = RecipesPagerAdapter(supportFragmentManager)
        showFragmentByTag(FavoritesFragment.TAG, false, null)
    }

    override fun itemCreated(pantryItem: Ingredient) {
        viewModel.saveItemToPantry(pantryItem)
    }

    override fun itemUpdated(pantryItem: Ingredient) {
        //handled elsewhere, but override required
    }

    private fun showFragmentByTag(tag: String,
                                 toBackStack: Boolean, bundle: Bundle?) {
        var fragment: Fragment? = supportFragmentManager.findFragmentByTag(tag)
        if (fragment == null) {
            if (FavoritesFragment.TAG == tag) {
                fragment = FavoritesFragment()
            } else if (PantryFragment.TAG == tag) {
                fragment = PantryFragment()
            }
        }
        if (fragment != null) {
            val ft = supportFragmentManager
                .beginTransaction()
            ft.replace(R.id.layoutMain, fragment!!, tag)
            if (toBackStack) {
                ft.addToBackStack(null)
            }
        if (bundle != null){
            fragment.setArguments(bundle)
        }
            ft.commit()
        }
    }
}
