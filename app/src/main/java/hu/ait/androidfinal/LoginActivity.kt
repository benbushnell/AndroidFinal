package hu.ait.androidfinal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider
import hu.ait.androidfinal.data.FirestoreUtil
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    companion object{
        const val REQUEST_ID = 100
        const val REQUEST_ID_DEMO = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        var gso : GoogleSignInOptions = GoogleSignInOptions.Builder(
            GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        var mgsic = GoogleSignIn.getClient(this, gso)
        val firebaseAuth = FirebaseAuth.getInstance()
        btnLogin.setOnClickListener {
            val loginIntent = mgsic.getSignInIntent()
            startActivityForResult(loginIntent, REQUEST_ID)
        }

    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        //updateUI(account) // Do what you should do if user is already signed in
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("auth", "Google sign in failed", e)
                // ...
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d("auth", "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("auth", "signInWithCredential:success")
                    val user = auth.currentUser
                    FirestoreUtil.initCurrentUserFirstTime {
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_LONG).show()
                        val loginIntent = Intent()
                        loginIntent.setClass(this, MainActivity::class.java )
                        startActivity(loginIntent)

                    }

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("auth", "signInWithCredential:failure", task.exception)
                    Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_LONG).show()
                }

                // ...
            }
    }

    fun checkExistingUser(){
        var firestoreDB = FirebaseFirestore.getInstance()
        var user = FirebaseAuth.getInstance().currentUser
        firestoreDB.collection("users").whereEqualTo("uid", user!!.uid)
            .get()
            .addOnSuccessListener {

            }
            .addOnFailureListener {
                firestoreDB.collection("users").document()
            }
    }
}