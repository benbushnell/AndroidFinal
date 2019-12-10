package hu.ait.androidfinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import hu.ait.androidfinal.adapter.RecipesAdapter
import hu.ait.androidfinal.api.RecipeAPI
import hu.ait.androidfinal.data.Base
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    //https://www.themealdb.com/api/json/v1/1/search.php?s=Arrabiata

    lateinit var recipeAdapter : RecipesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()

        layoutRefresh.setOnRefreshListener {
            getRandomRecipes()
        }

        getRandomRecipes()

        val db = FirebaseFirestore.getInstance()

    }

    private fun  getRandomRecipes(){
        layoutRefresh.isRefreshing = true
        val recipeIdList = mutableListOf("52903", "52831", "52945", "52813")
        var i = 0
        while (i < 4) {
            //val recipeSearch = recipeAPI.searchByMainIngredient("chicken_breast")
            val recipeGet = RecipeAPI().getRecipeById(recipeIdList[i])
            recipeGet.enqueue(object : Callback<Base> {
                override fun onFailure(call: Call< Base>, t: Throwable) {
                    //tvRecipeName.text = t.message
                    layoutRefresh.isRefreshing = false
                    Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
                    Log.d("response", t.message!!)
                }

                override fun onResponse(call: Call<Base>, response: Response<Base>) {
                    layoutRefresh.isRefreshing = false
                    Log.d("response", response.body().toString())

                    val recipeResponses = response.body()

                    recipeResponses?.let{
                        recipeAdapter.addRecipe(recipeResponses.meals[0])
                    }
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

    private fun initRecyclerView(){
        recipeAdapter = RecipesAdapter(this, mutableListOf())
        recyclerRecipes.adapter = recipeAdapter
        recyclerRecipes.layoutManager = GridLayoutManager(this, 2)
    }
}
