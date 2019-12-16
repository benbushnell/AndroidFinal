package hu.ait.androidfinal.fragments

import android.content.Intent
import android.os.Bundle
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
import hu.ait.androidfinal.*
import hu.ait.androidfinal.adapter.PantryAdapter
import hu.ait.androidfinal.data.Ingredient
import hu.ait.androidfinal.data.PantryRepository
import hu.ait.androidfinal.data.RecipeAPIRepo
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import kotlinx.android.synthetic.main.pantry_fragment.*
import java.io.Serializable

class PantryFragment : Fragment() {

    companion object {
        const val TAG_ITEM_DIALOG = "TAG_ITEM_DIALOG"
        const val TAG = "PantryFragment"
    }

    private lateinit var viewModel: RecipeViewModel
    lateinit var pantryAdapter: PantryAdapter
    val pantryRepository = PantryRepository()
    var includedItems : MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.pantry_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RecipeViewModel::class.java)
        pantryAdapter = PantryAdapter(activity!!)
        recyclerPantry.adapter = pantryAdapter
        recyclerPantry.layoutManager = LinearLayoutManager(activity)
        recyclerPantry.layoutManager = GridLayoutManager(activity, 2)
        recyclerPantry.itemAnimator = SlideInLeftAnimator()
        var allPostsListener = pantryRepository.getPantryItems().addSnapshotListener(
            object: EventListener<QuerySnapshot> {
                override fun onEvent(querySnapshot: QuerySnapshot?, e: FirebaseFirestoreException?) {

                    if (e != null) {
                        return
                    }
                    for (dc in querySnapshot!!.documentChanges) {
                        when (dc.type) {
                            DocumentChange.Type.ADDED -> {
                                val item = dc.document.toObject(Ingredient::class.java)
                                pantryAdapter.addItem(item)
                                if(item.include && !(item.name in includedItems)){
                                    includedItems.add(item.name!!)
                                }
                            }
                            DocumentChange.Type.MODIFIED -> {
                                val item = dc.document.toObject(Ingredient::class.java)

                                if (item.name in includedItems && !item.include) {
                                    includedItems.remove(item.name)
                                } else {
                                    includedItems.add(item.name!!)
                                }
                            }
                            DocumentChange.Type.REMOVED -> {
                                val item = dc.document.toObject(Ingredient::class.java)
                                if (item.name in includedItems) includedItems.remove(item.name)
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
                if(meals.isNullOrEmpty()){
                    Toast.makeText(context, getString(R.string.no_results), Toast.LENGTH_SHORT).show()
                } else {
                    val intent = Intent()
                    intent.setClass(context as MainActivity, SearchResultsActivity::class.java)
                    intent.putExtra("meals", meals as Serializable)
                    this.startActivity(intent)
                }
            })
        }
    }

}