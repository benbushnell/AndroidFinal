package hu.ait.androidfinal.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import hu.ait.androidfinal.fragments.PantryFragment
import hu.ait.androidfinal.fragments.RecipeListFragment

class RecipesPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm){
    override fun getItem(position: Int): Fragment {
        return if (position == 0){
            RecipeListFragment()
        } else{
            PantryFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (position ==0) "Favorite Recipes" else "My Pantry"
    }

    override fun getCount(): Int {
        return 2
    }

}