package hu.ait.androidfinal.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeAPI{

    //https://www.themealdb.com/api/json/v1/1/search.php?s=Arrabiata

    @GET("api/json/v2/9973533/search.php")
    fun searchMealName(
        @Query("s") field: String
    ): Call<Base>

    //https://themealdb.com/api/json/v2/9973533/filter.php?i=cheese
    @GET("api/json/v2/9973533/filter.php")
    fun searchByMainIngredient(
        @Query("i") field: String
    ): Call<Base>

    //https://www.themealdb.com/api/json/v2/9973533/lookup.php?i=52779
    @GET("api/json/v2/9973533/lookup.php")
    fun getRecipyById(
        @Query("i") field: String
    ): Call<Base>
}