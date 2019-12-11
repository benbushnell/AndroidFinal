package hu.ait.androidfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import hu.ait.androidfinal.data.FirestoreUtil
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {


    companion object{
        const val REQUEST_ID = 100
        const val REQUEST_ID_DEMO = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var gso : GoogleSignInOptions = GoogleSignInOptions.Builder(
            GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        var mgsic = GoogleSignIn.getClient(this, gso)

        btnLogin.setOnClickListener {
            val loginIntent = mgsic.getSignInIntent()
            startActivityForResult(loginIntent, LoginActivity.REQUEST_ID)
        }

    }

    override fun onStart() {
        super.onStart()
        var account = GoogleSignIn.getLastSignedInAccount(this)
        //updateUI(account) // Do what you should do if user is already signed in
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100){
            FirestoreUtil.initCurrentUserFirstTime {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_LONG).show()
                val loginIntent = Intent()
                loginIntent.setClass(this, MainActivity::class.java )
                startActivity(loginIntent)
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            }
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