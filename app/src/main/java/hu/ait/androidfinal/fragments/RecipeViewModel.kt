package hu.ait.androidfinal.fragments

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import hu.ait.androidfinal.api.RecipeAPI
import hu.ait.androidfinal.data.*
import kotlinx.android.synthetic.main.favorites_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeViewModel : ViewModel() {

    companion object{
        const val TAG = "Recipe_VIEW_MODEL"
    }
    private var favoritesRepository = FavoritesRepository()
    private var pantryRepository = PantryRepository()
    private val savedFavorites : MutableLiveData<List<Meal>> = MutableLiveData()
    private val savedPantryItems : MutableLiveData<List<Ingredient>> = MutableLiveData()


    // save favorite to firebase
    fun saveFavoriteToRepo(favorite: Meal){
        favoritesRepository.saveFavorite(favorite).addOnFailureListener {
            Log.e(TAG,"Failed to save Recipe!")
        }
    }

    // get realtime updates from firebase regarding saved favorites
    fun getSavedFavorites(): LiveData<List<Meal>>{
        favoritesRepository.getSavedFavorites().addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                savedFavorites.value = null
                return@EventListener
            }

            var savedFavoritesList : MutableList<Meal> = mutableListOf()
            for (doc in value!!) {
                var favoriteItem = doc.toObject(Meal::class.java)
                savedFavoritesList.add(favoriteItem)
            }
            savedFavorites.value = savedFavoritesList
        })

        return savedFavorites
    }

    fun deleteFavorite(favoriteItem: Meal){
        favoritesRepository.deleteFavorite(favoriteItem).addOnFailureListener {
            Log.e(TAG,"Failed to delete Recipe")
        }
    }

    fun saveItemToPantry(item: Ingredient){
        pantryRepository.saveItem(item).addOnFailureListener {
            Log.e(TAG,"Failed to save Recipe!")
        }
    }



    fun getPantryItems() : LiveData<List<Ingredient>>{
        var savedPantryList : MutableList<Ingredient> = mutableListOf()
        pantryRepository.getPantryItems().addSnapshotListener(EventListener<QuerySnapshot> {value, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                savedPantryItems.value = null
                return@EventListener
            }

            for (doc in value!!) {
                var pantryItem = doc.toObject(Ingredient::class.java)
                savedPantryList.add(pantryItem)
            }
            savedPantryItems.value = savedPantryList
        })

        return savedPantryItems
    }

}

