package hu.ait.androidfinal

import androidx.appcompat.app.AppCompatActivity
import retrofit2.Retrofit
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import hu.ait.androidfinal.adapter.RecipesAdapter
import hu.ait.androidfinal.data.Recipe
import hu.ait.androidfinal.data.RecipeAPI
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    val spoonUrl = "https://api.spoonacular.com"
    val apiKey = "8c09193246b84f0e853467ad32076f8c"
    lateinit var recipeAdapter : RecipesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()

        val retrofit =
            Retrofit.Builder().baseUrl(spoonUrl).addConverterFactory(GsonConverterFactory.create()).build()
        val recipeAPI = retrofit.create(RecipeAPI::class.java)

        var i = 0
       // while (i < 20) {
            val randomRecipe = recipeAPI.getRandomRecipes("1", apiKey)
            randomRecipe.enqueue(object : Callback<Recipe> {
                override fun onFailure(call: Call<Recipe>, t: Throwable) {
                    //tvRecipeName.text = t.message
                    Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
                    Log.d("response", t.message!!)
                }

                override fun onResponse(call: Call<Recipe>, response: Response<Recipe>) {
                    recipeAdapter.addRecipe(response.body()!!)
                    Toast.makeText(this@MainActivity, "${response.body()}", Toast.LENGTH_SHORT).show()
                    Log.d("response", randomRecipe.toString())
                    Log.d("response", call.toString())
                    Log.d("response", response.toString())
                    Log.d("response", response.body().toString())
                }
            })
          //  i++
        //}
    }

    private fun initRecyclerView(){
        recipeAdapter = RecipesAdapter(this, mutableListOf())
        recyclerRecipes.adapter = recipeAdapter
    }
}
