package hu.ait.androidfinal

import androidx.appcompat.app.AppCompatActivity
import retrofit2.Retrofit
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import hu.ait.androidfinal.adapter.RecipesAdapter
import hu.ait.androidfinal.data.Base
import hu.ait.androidfinal.data.RecipeAPI
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    //https://www.themealdb.com/api/json/v1/1/search.php?s=Arrabiata

    val apiURL = "https://www.themealdb.com/"
    lateinit var recipeAdapter : RecipesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()

        val retrofit =
            Retrofit.Builder().baseUrl(apiURL).addConverterFactory(GsonConverterFactory.create()).build()
        val recipeAPI = retrofit.create(RecipeAPI::class.java)

        val recipeIdList = mutableListOf<String>("52903", "52831", "52945", "52813")
        var i = 0
        while (i < 4) {
            //val recipeSearch = recipeAPI.searchByMainIngredient("chicken_breast")
            val recipeGet = recipeAPI.getRecipeById(recipeIdList[i])
            recipeGet.enqueue(object : Callback<Base> {
                override fun onFailure(call: Call<Base>, t: Throwable) {
                    //tvRecipeName.text = t.message
                    Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
                    Log.d("response", t.message!!)
                }

                override fun onResponse(call: Call<Base>, response: Response<Base>) {
                    recipeAdapter.addRecipe(response.body()!!.meals[0])
                   // Toast.makeText(this@MainActivity, "${response.body()}", Toast.LENGTH_SHORT).show()
                    Log.d("response", recipeGet.toString())
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
