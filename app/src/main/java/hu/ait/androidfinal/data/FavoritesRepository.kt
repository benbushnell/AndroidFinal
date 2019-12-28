package hu.ait.androidfinal.data

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class FavoritesRepository {

    private var firestoreDB = FirebaseFirestore.getInstance()
    private var user = FirebaseAuth.getInstance().currentUser
    private val baseUserPath = firestoreDB.collection("users").document(user!!.uid).collection("favorites")


    // save favorite to firestore
    fun saveFavorite(favorite: Meal): Task<Void> {
        //var
        val documentReference = baseUserPath.document(favorite.idMeal!!)
        return documentReference.set(favorite)
    }

    // get saved favorites from firestore
    fun getSavedFavorites(): CollectionReference {
        val collectionReference = baseUserPath
        return collectionReference
    }

    //delete favorite from collection
    fun deleteFavorite(favorite: Meal): Task<Void> {
        val documentReference =  baseUserPath.document(favorite.idMeal!!)

        return documentReference.delete()
    }

}