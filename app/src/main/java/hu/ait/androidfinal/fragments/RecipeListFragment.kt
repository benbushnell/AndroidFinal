package hu.ait.androidfinal.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import hu.ait.androidfinal.R
import hu.ait.androidfinal.adapter.RecipesAdapter
import hu.ait.androidfinal.api.RecipeAPI
import hu.ait.androidfinal.data.Base
import hu.ait.androidfinal.data.Meal
import kotlinx.android.synthetic.main.recipe_list_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeListFragment : Fragment() {

    companion object {
        fun newInstance() = RecipeListFragment()
    }

    private lateinit var viewModel: RecipeListViewModel

    lateinit var recipeAdapter: RecipesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.recipe_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RecipeListViewModel::class.java)

        recipeAdapter = RecipesAdapter(activity!!)
        recyclerRecipes.adapter = recipeAdapter
        recyclerRecipes.layoutManager = GridLayoutManager(activity, 2)

        layoutRefresh.setOnRefreshListener {
            getRandomRecipes()
        }


    }


    private fun  getRandomRecipes(){
        layoutRefresh.isRefreshing = true
        val recipeIdList = mutableListOf("52903", "52831", "52945", "52813")
        val resultList = mutableListOf<Meal>()
        var i = 0
        while (i < 4) {
            //val recipeSearch = recipeAPI.searchByMainIngredient("chicken_breast")
            val recipeGet = RecipeAPI().getRecipeById(recipeIdList[i])
            recipeGet.enqueue(object : Callback<Base> {
                override fun onFailure(call: Call<Base>, t: Throwable) {
                    //tvRecipeName.text = t.message
                    layoutRefresh.isRefreshing = false
                    Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
                    Log.d("response", t.message!!)
                }

                override fun onResponse(call: Call<Base>, response: Response<Base>) {
                    layoutRefresh.isRefreshing = false
                    Log.d("response", response.body().toString())

                    val recipeResponse = response.body()
                    recipeResponse?.let {
                        recipeAdapter.addRecipe(recipeResponse.meals[0])
                    }
                    Log.d("list", resultList.toString())
                    // Toast.makeText(this@MainActivity, "${response.body()}", Toast.LENGTH_SHORT).show()
                    //Log.d("response", recipeGet.toString())
                    Log.d("response", call.toString())
                    Log.d("response", response.toString())
                    Log.d("response", response.body().toString())
                }
            })
            i++
        }
    }




}
