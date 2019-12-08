package hu.ait.androidfinal.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeAPI{

    //https://www.themealdb.com/api/json/v1/1/search.php?s=Arrabiata

    @GET("api/json/v1/1/search.php")
    fun searchMealName(
        @Query("s") field: String
    ): Call<Base>
}