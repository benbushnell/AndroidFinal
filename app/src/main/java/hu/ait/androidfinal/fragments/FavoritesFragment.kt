package hu.ait.androidfinal.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import hu.ait.androidfinal.R
import hu.ait.androidfinal.RecipeViewModel
import hu.ait.androidfinal.adapter.FavoritesAdapter
import hu.ait.androidfinal.data.FavoritesRepository
import hu.ait.androidfinal.data.Meal
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.favorites_fragment.*

class FavoritesFragment : Fragment() {

    companion object {
        const val TAG = "FavoritesFragment"
    }

    private lateinit var viewModel: RecipeViewModel
    private val favoritesRepo = FavoritesRepository()
    lateinit var favoritesAdapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorites_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RecipeViewModel::class.java)
        favoritesAdapter = FavoritesAdapter(activity!!)
        recyclerRecipes.adapter = favoritesAdapter
        recyclerRecipes.itemAnimator = SlideInUpAnimator(OvershootInterpolator(1f))
        recyclerRecipes.layoutManager = GridLayoutManager(activity, 2)
        val allFavesListener = favoritesRepo.getSavedFavorites().addSnapshotListener(
            object: EventListener<QuerySnapshot> {
                override fun onEvent(querySnapshot: QuerySnapshot?, e: FirebaseFirestoreException?) {

                    if (e != null) {
                        return
                    }
                    for (dc in querySnapshot!!.documentChanges) {
                        when (dc.type) {
                            DocumentChange.Type.ADDED -> {
                                val item = dc.document.toObject(Meal::class.java)
                                favoritesAdapter.addRecipe(item)

                            }
                            DocumentChange.Type.MODIFIED -> {
                                val item = dc.document.toObject(Meal::class.java)
                            }
                            DocumentChange.Type.REMOVED -> {
                                val item = dc.document.toObject(Meal::class.java)
                                favoritesAdapter.removeIngredientByName(item)
                            }
                        }
                    }
                }
            })
    }
}
