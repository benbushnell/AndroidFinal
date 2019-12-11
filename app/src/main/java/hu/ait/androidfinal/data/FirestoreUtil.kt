package hu.ait.androidfinal.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.NullPointerException

object FirestoreUtil {
    private val firestoreInstance : FirebaseFirestore by lazy {FirebaseFirestore.getInstance()}

    private val currentUserDocRef : DocumentReference
        get() = firestoreInstance.document("users/${FirebaseAuth.getInstance().uid
            ?: throw NullPointerException("UID is null")}")

    fun initCurrentUserFirstTime(onComplete: () -> Unit) {
        currentUserDocRef.get().addOnSuccessListener {documentSnapshot ->
            if(!documentSnapshot.exists()){
                Log.d("fire", FirebaseAuth.getInstance().currentUser!!.uid.toString())
                val newUser = User(FirebaseAuth.getInstance().currentUser!!.uid)
                currentUserDocRef.set(newUser).addOnSuccessListener {
                    currentUserDocRef.collection("Favorites")
                    currentUserDocRef.collection("Pantry")
                    onComplete()
                }
            }
            else{
                onComplete()
            }
        }
    }
    /*fun getCurrentUser(onComplete : (User) -> Unit) {
        currentUserDocRef.get()
            .addOnSuccessListener {
                onComplete(it.toObject(User!!::class.java))
            }
    }*/
}