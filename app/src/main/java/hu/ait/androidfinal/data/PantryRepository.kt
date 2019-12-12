package hu.ait.androidfinal.data

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class PantryRepository {

    val TAG = "PANTRY_REPOSITORY"
    private var firestoreDB = FirebaseFirestore.getInstance()
    private var user = FirebaseAuth.getInstance().currentUser
    private val baseUserPath = firestoreDB.collection("users").document(user!!.uid).collection("pantry")


    //save pantry item to firestore
    fun saveItem(item: Ingredient): Task<Void> {
        var documentReference = baseUserPath.document(item.name!!)
        return documentReference.set(item)
    }

    fun getPantryItems(): CollectionReference {
        var collectionReference = baseUserPath
        return collectionReference
    }

    fun deletePantryItem(item: Ingredient): Task<Void> {
        var documentReference = baseUserPath.document(item.name!!)

        return documentReference.delete()
    }

    fun editPantryItem(item: Ingredient): Task<Void> {
        var documentReference = baseUserPath.document(item.name!!)
        return documentReference.update("quantity", item.quantity)
    }
}