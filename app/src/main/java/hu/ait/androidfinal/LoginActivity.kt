package hu.ait.androidfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {


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
            startActivityForResult(loginIntent, 100)
        }

    }

    override fun onStart() {
        super.onStart()
        var account = GoogleSignIn.getLastSignedInAccount(this)
        //updateUI(account) // Do what you should do if user is already signed in!!
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            Toast.makeText(this, "Login Successful", Toast.LENGTH_LONG).show()
        }
    }
}