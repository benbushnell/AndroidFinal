package hu.ait.androidfinal.data

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class FavoritesRepository {

    val TAG = "FAVORITES_RESPOSITORY"
    var firestoreDB = FirebaseFirestore.getInstance()
    var user = FirebaseAuth.getInstance().currentUser
    val baseUserPath = firestoreDB.collection("users").document(user!!.uid).collection("favorites")


    // save favorite to firestore
    fun saveFavorite(favorite: Meal): Task<Void> {
        //var
        var documentReference = baseUserPath.document(favorite.idMeal!!)
        return documentReference.set(favorite)
    }

    // get saved favorites from firestore
    fun getSavedFavorites(): CollectionReference {
        var collectionReference = baseUserPath
        return collectionReference
    }

    //delete favorite from collection
    fun deleteFavorite(favorite: Meal): Task<Void> {
        var documentReference =  baseUserPath.document(favorite.idMeal!!)

        return documentReference.delete()
    }

}