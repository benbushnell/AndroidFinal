package hu.ait.androidfinal.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeAPI{

    //https://api.spoonacular.com/recipes/random?number=1&apiKey=8c09193246b84f0e853467ad32076f8c

    @GET("/recipes/random")
    fun getRandomRecipes(
        @Query("number") number: String,
        @Query("apiKey") apiKey: String
    ): Call<Recipe>
}