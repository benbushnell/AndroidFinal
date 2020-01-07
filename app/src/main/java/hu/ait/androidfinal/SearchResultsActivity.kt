package hu.ait.androidfinal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import hu.ait.androidfinal.adapter.SearchResultsAdapter
import hu.ait.androidfinal.data.Meal
import kotlinx.android.synthetic.main.activity_search_results.*

class SearchResultsActivity : AppCompatActivity(){

    private lateinit var mealList: MutableList<Meal>

    lateinit var searchResultsAdapter: SearchResultsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)
        mealList = intent.getSerializableExtra("meals") as MutableList<Meal>
        initRecycler()

    }

    private fun initRecycler() {
        searchResultsAdapter = SearchResultsAdapter(this)
        recyclerSearchResults.adapter = searchResultsAdapter
        recyclerSearchResults.layoutManager = GridLayoutManager(this, 2)
        searchResultsAdapter.replaceItems(mealList)

    }
}