package hu.ait.androidfinal.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager

import hu.ait.androidfinal.R
import hu.ait.androidfinal.adapter.FavoritesAdapter
import hu.ait.androidfinal.adapter.SearchResultsAdapter
import hu.ait.androidfinal.data.Meal
import kotlinx.android.synthetic.main.favorites_fragment.*
import kotlinx.android.synthetic.main.fragment_search_results.*


class SearchResultsFragment : Fragment() {

    companion object {
        const val TAG = "SearchResultsFragment"
    }

    private lateinit var viewModel: RecipeViewModel
    private lateinit var bundle: Bundle
    private lateinit var mealList: MutableList<Meal>

    lateinit var searchResultsAdapter: SearchResultsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bundle = arguments!!
        mealList = bundle.getSerializable("TOP_TEN") as MutableList<Meal>
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_search_results, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RecipeViewModel::class.java)
        searchResultsAdapter = SearchResultsAdapter(context!!)
        recyclerSearchResults.adapter = searchResultsAdapter
        recyclerSearchResults.layoutManager = GridLayoutManager(context!!, 2)
        searchResultsAdapter.replaceItems(mealList)

    }
}
