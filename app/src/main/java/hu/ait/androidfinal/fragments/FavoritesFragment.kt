package hu.ait.androidfinal.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

import hu.ait.androidfinal.R
import hu.ait.androidfinal.adapter.FavoritesAdapter
import hu.ait.androidfinal.api.RecipeAPI
import hu.ait.androidfinal.data.Base
import hu.ait.androidfinal.data.FavoritesRepository
import hu.ait.androidfinal.data.Ingredient
import hu.ait.androidfinal.data.Meal
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.favorites_fragment.*
import kotlinx.android.synthetic.main.recipe_list_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoritesFragment : Fragment() {

    companion object {
        fun newInstance() = FavoritesFragment()
        const val TAG = "FavoritesFragment"
    }



    private lateinit var viewModel: RecipeViewModel
    val favoritesRepo = FavoritesRepository()
    lateinit var favoritesAdapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorites_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RecipeViewModel::class.java)
        favoritesAdapter = FavoritesAdapter(activity!!)
        recyclerRecipes.adapter = favoritesAdapter
        recyclerRecipes.itemAnimator = SlideInUpAnimator(OvershootInterpolator(1f))
        recyclerRecipes.layoutManager = GridLayoutManager(activity, 2)
        var allFavesListener = favoritesRepo.getSavedFavorites().addSnapshotListener(
            object: EventListener<QuerySnapshot> {
                override fun onEvent(querySnapshot: QuerySnapshot?, e: FirebaseFirestoreException?) {

                    if (e != null) {
                        return
                    }
                    for (dc in querySnapshot!!.documentChanges) {
                        when (dc.type) {
                            DocumentChange.Type.ADDED -> {
                                val item = dc.document.toObject(Meal::class.java)
                                favoritesAdapter.addRecipe(item)

                            }
                            DocumentChange.Type.MODIFIED -> {
                                val item = dc.document.toObject(Meal::class.java)
                            }
                            DocumentChange.Type.REMOVED -> {
                                val item = dc.document.toObject(Meal::class.java)
                                favoritesAdapter.removeIngredientByName(item)
                            }
                        }
                    }
                }
            })
        /**
        layoutRefresh.setOnRefreshListener {
            layoutRefresh.isRefreshing = true
            viewModel.getSavedFavorites().observe(this, Observer {savedFavorites -> favoritesAdapter.replaceItems(savedFavorites)
            })
                  layoutRefresh.isRefreshing = false
    }
    **/
        /**

        btnAddFav.setOnClickListener {
            layoutRefresh.isRefreshing = true
            viewModel.getSavedFavorites().observe(this, Observer {savedFavorites -> favoritesAdapter.replaceItems(savedFavorites)
            })
            val recipeGet = RecipeAPI().getRecipeById("52903")
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
                        viewModel.saveFavoriteToRepo(recipeResponse.meals[0])
                    }
                    // Toast.makeText(this@MainActivity, "${response.body()}", Toast.LENGTH_SHORT).show()
                    //Log.d("response", recipeGet.toString())
                    Log.d("response", call.toString())
                    Log.d("response", response.toString())
                    Log.d("response", response.body().toString())
                }
            })
        } **/
    }
    /**
    override fun onResume() {
        super.onResume()
        viewModel.getSavedFavorites().observe(this, Observer {savedFavorites ->
            for (fav in favoritesAdapter.recipesList) {
                if (fav !in savedFavorites) {
                    favoritesAdapter.removeIngredientByName(fav)
                }
            }
            })
        }
    **/

    //Add swipe refresh isRefreshing statements back in




    /**
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
                        favoritesAdapter.addRecipe(recipeResponse.meals[0])
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
    **/




}
