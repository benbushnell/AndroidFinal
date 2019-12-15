package hu.ait.androidfinal.data

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import hu.ait.androidfinal.api.RecipeAPI
import kotlinx.android.synthetic.main.favorites_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeAPIRepo {
    private  var recipeApi: RecipeAPI = RecipeAPI.invoke()

    public fun searchByIngredients(ingredientsString : String) : MutableLiveData<Base>{
        val results : MutableLiveData<Base> = MutableLiveData()
        val recipesGet = recipeApi.searchByIngredients(ingredientsString)

        recipesGet.enqueue( object : Callback<Base> {
            override fun onFailure(call: Call<Base>, t: Throwable) {
                Log.d("response", t.message!!)
            }

            override fun onResponse(call: Call<Base>, response: Response<Base>) {
                Log.d("response", response.body().toString())
                results.value = response.body()

            }
        })
        return results
    }

    fun getRecipeById(id : String) : MutableLiveData<Base>{
        val result : MutableLiveData<Base> = MutableLiveData()
        val recipeGet = recipeApi.getRecipeById(id)
        Log.d("api", recipeGet.toString())

        recipeGet.enqueue( object : Callback<Base> {
            override fun onFailure(call: Call<Base>, t: Throwable) {
                Log.d("response", t.message!!)
            }

            override fun onResponse(call: Call<Base>, response: Response<Base>) {
                Log.d("response", response.body().toString())
                result.value = response.body()

            }
        })
        return result
    }




}
