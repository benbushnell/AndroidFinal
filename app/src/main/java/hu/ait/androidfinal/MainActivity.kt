package hu.ait.androidfinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import hu.ait.androidfinal.adapter.PantryAdapter
import hu.ait.androidfinal.adapter.RecipesPagerAdapter
import hu.ait.androidfinal.data.Ingredient
import hu.ait.androidfinal.fragments.FavoritesFragment
import hu.ait.androidfinal.fragments.NewPantryItemDialog
import hu.ait.androidfinal.fragments.PantryFragment
import hu.ait.androidfinal.fragments.RecipeViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.favorites_fragment.*

class MainActivity : AppCompatActivity(), NewPantryItemDialog.ItemHandler {

    //https://www.themealdb.com/api/json/v1/1/search.php?s=Arrabiata

    private lateinit var viewModel: RecipeViewModel
    lateinit var pantryAdapter: PantryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(RecipeViewModel::class.java)
        pantryAdapter = PantryAdapter(this)
        //viewpager.adapter = RecipesPagerAdapter(supportFragmentManager)

        navigation.setOnNavigationItemSelectedListener(myOnNavigationItemSelectedListener)

        showFragmentByTag(PantryFragment.TAG, false)
    }

    override fun itemCreated(pantryItem: Ingredient) {
        viewModel.saveItemToPantry(pantryItem)
        pantryAdapter.addItem(pantryItem)
    }

    override fun itemUpdated(pantryItem: Ingredient) {
        //viewModel.
    }

    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().func().commit()
    }

    fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
        supportFragmentManager.inTransaction{replace(frameId, fragment)}
    }

    public fun showFragmentByTag(tag: String,
                                 toBackStack: Boolean) {
        var fragment: Fragment? = supportFragmentManager.findFragmentByTag(tag)
        if (fragment == null) {
            Log.d("fff", "here")
            if (FavoritesFragment.TAG == tag) {
                fragment = FavoritesFragment()
            } else if (PantryFragment.TAG == tag) {
                fragment = PantryFragment()
            } //else if (RecipeDetailsFragment.TAG == tag) {
                //fragment = RecipeDetailsFragment()
            }
        if (fragment != null) {
            val ft = supportFragmentManager
                .beginTransaction()
            ft.replace(R.id.layoutMain, fragment!!, tag)
            if (toBackStack) {
                ft.addToBackStack(null)
            }
            ft.commit()
        }
        }

    private val myOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                showFragmentByTag(FavoritesFragment.TAG, true)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                showFragmentByTag(PantryFragment.TAG, true)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

}
