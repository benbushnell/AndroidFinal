package hu.ait.androidfinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import hu.ait.androidfinal.adapter.FavoritesAdapter
import hu.ait.androidfinal.adapter.PantryAdapter
import hu.ait.androidfinal.adapter.RecipesPagerAdapter
import hu.ait.androidfinal.data.Ingredient
import hu.ait.androidfinal.fragments.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.favorites_fragment.*

class MainActivity : AppCompatActivity(), NewPantryItemDialog.ItemHandler {

    //https://www.themealdb.com/api/json/v1/1/search.php?s=Arrabiata

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
        //do nothing, just for override
    }

    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().func().commit()
    }

    fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
        supportFragmentManager.inTransaction{replace(frameId, fragment)}
    }

    fun showFragmentByTag(tag: String,
                                 toBackStack: Boolean, bundle: Bundle?) {
        var fragment: Fragment? = supportFragmentManager.findFragmentByTag(tag)
        if (fragment == null) {
            if (FavoritesFragment.TAG == tag) {
                fragment = FavoritesFragment()
            } else if (PantryFragment.TAG == tag) {
                fragment = PantryFragment()
            } else if (RecipeDetailsFragment.TAG == tag) {
                fragment = RecipeDetailsFragment()
            } else if (SearchResultsFragment.TAG == tag) {
                fragment = SearchResultsFragment()
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
