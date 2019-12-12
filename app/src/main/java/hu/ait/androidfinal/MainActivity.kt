package hu.ait.androidfinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager.widget.PagerAdapter
import com.google.firebase.firestore.FirebaseFirestore
import hu.ait.androidfinal.adapter.FavoritesAdapter
import hu.ait.androidfinal.adapter.RecipesPagerAdapter
import hu.ait.androidfinal.api.RecipeAPI
import hu.ait.androidfinal.data.Base
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    //https://www.themealdb.com/api/json/v1/1/search.php?s=Arrabiata

    lateinit var recipeAdapter : FavoritesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewpager.adapter = RecipesPagerAdapter(supportFragmentManager)


        val db = FirebaseFirestore.getInstance()

    }

}
