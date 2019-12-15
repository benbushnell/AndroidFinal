package hu.ait.androidfinal.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import hu.ait.androidfinal.MainActivity
import hu.ait.androidfinal.R
import hu.ait.androidfinal.adapter.PantryAdapter
import hu.ait.androidfinal.api.RecipeAPI
import hu.ait.androidfinal.data.Ingredient
import hu.ait.androidfinal.data.PantryRepository
import hu.ait.androidfinal.data.RecipeAPIRepo
import hu.ait.androidfinal.fragments.NewPantryItemDialog.Companion.TAG_ITEM_DIALOG
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import kotlinx.android.synthetic.main.favorites_fragment.*
import kotlinx.android.synthetic.main.pantry_fragment.*
import kotlinx.android.synthetic.main.pantry_list_item.*
import java.io.Serializable

class PantryFragment : Fragment() {

    companion object {
        const val TAG_ITEM_DIALOG = "TAG_ITEM_DIALOG"
        const val TAG = "PantryFragment"
    }

    private lateinit var viewModel: RecipeViewModel
    lateinit var pantryAdapter: PantryAdapter
    val pantryRepository = PantryRepository()
    var includedItems : MutableList<Ingredient> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView =  inflater.inflate(R.layout.pantry_fragment, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RecipeViewModel::class.java)
        pantryAdapter = PantryAdapter(activity!!)
        val fm = fragmentManager
        recyclerPantry.adapter = pantryAdapter
        recyclerPantry.layoutManager = LinearLayoutManager(activity)
        //viewModel.getPantryItems().observe(viewLifecycleOwner, Observer {savedPantryItem -> pantryAdapter.replaceItems(savedPantryItem.toMutableList())
        //})
        Log.d("observe", viewModel.getPantryItems().hasActiveObservers().toString())
        recyclerPantry.layoutManager = GridLayoutManager(activity, 2)
        recyclerPantry.itemAnimator = SlideInLeftAnimator()
        var allPostsListener = pantryRepository.getPantryItems().addSnapshotListener(
            object: EventListener<QuerySnapshot> {
                override fun onEvent(querySnapshot: QuerySnapshot?, e: FirebaseFirestoreException?) {

                    if (e != null) {
                        Toast.makeText(activity, "listen error: ${e.message}", Toast.LENGTH_LONG).show()
                        return
                    }
                    for (dc in querySnapshot!!.getDocumentChanges()) {
                        when (dc.getType()) {
                            DocumentChange.Type.ADDED -> {
                                val item = dc.document.toObject(Ingredient::class.java)
                                pantryAdapter.addItem(item)
                                if(item.include){
                                    includedItems.add(item)
                                }
                            }
                            DocumentChange.Type.MODIFIED -> {
                                val item = dc.document.toObject(Ingredient::class.java)
                                if (item in includedItems && !item.include) includedItems.remove(item)
                                Toast.makeText(activity, "update: ", Toast.LENGTH_LONG).show()
                            }
                            DocumentChange.Type.REMOVED -> {
                                val item = dc.document.toObject(Ingredient::class.java)
                                if (item in includedItems) includedItems.remove(item)
                            }
                        }
                    }
                }
            })


        btnAddPantryItem.setOnClickListener {
            NewPantryItemDialog()
                .show(activity!!.supportFragmentManager, TAG_ITEM_DIALOG)
        }

        btnSearchByIngredients.setOnClickListener {
            val recipeApiRepo = RecipeAPIRepo()
            recipeApiRepo.searchByIngredients(
                viewModel.getIncludedString(includedItems)
            ).observe(viewLifecycleOwner, Observer {base ->
                var meals = base.meals
                var bundle = Bundle()
                bundle.putSerializable("TOP_TEN", meals as Serializable)
                (context as MainActivity).showFragmentByTag(SearchResultsFragment.TAG, true, bundle)
            })

        }
    }

}