package hu.ait.androidfinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import hu.ait.androidfinal.adapter.FavoritesAdapter

class MainActivity : AppCompatActivity() {

    //https://www.themealdb.com/api/json/v1/1/search.php?s=Arrabiata

    lateinit var recipeAdapter : FavoritesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = FirebaseFirestore.getInstance()

    }

}
