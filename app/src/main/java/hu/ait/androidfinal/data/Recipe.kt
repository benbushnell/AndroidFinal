package hu.ait.androidfinal.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// result generated from /json

data class Recipe(
    //@PrimaryKey(autoGenerate = true) We don't actually want to save this though. . . do we?
    //var itemId: Long?, (i added this?)
    val vegetarian: Boolean?,
    val vegan: Boolean?,
    val glutenFree: Boolean?,
    val dairyFree: Boolean?,
    val veryHealthy: Boolean?,
    val cheap: Boolean?,
    //@ColumnInfo(name = "popular")
    val veryPopular: Boolean?,
    val sustainable: Boolean?,
    val weightWatcherSmartPoints: Number?,
    val gaps: String?,
    val lowFodmap: Boolean?,
    val ketogenic: Boolean?,
    val whole30: Boolean?,
    //@ColumnInfo(name = "source")
    val sourceUrl: String?,
    val spoonacularSourceUrl: String?,
    val aggregateLikes: Number?,
    val spoonacularScore: Number?,
    val healthScore: Number?,
    val creditsText: String?,
    val license: String?,
    val sourceName: String?,
    val pricePerServing: Number?,
    val extendedIngredients: List<ExtendedIngredient>?,//I changed this from and to Extended ingredients
    val id: Number?,
    val title: String?,
    val readyInMinutes: Number?,
    val servings: Number?,
    val image: String?,
    val imageType: String?,
    val cuisines: List<Any>?,
    val dishTypes: List<String>?,
    val diets: List<Any>?,
    val occasions: List<Any>?,
    val winePairing: WinePairing?,
    val instructions: String?,
    val analyzedInstructions: List<Any>?
)

data class ExtendedIngredient(
    val id: Number?,
    val aisle: String?,
    val image: String?,
    val consitency: String?,
    val name: String?,
    val original: String?,
    val originalString: String?,
    val originalName: String?,
    val amount: Number?,
    val unit: String?,
    val meta: List<String>?,
    val metaInformation: List<String>?,
    val measures: Measures?
)

data class Measures(val us: Us?, val metric: Metric?)

data class Metric(val amount: Number?, val unitShort: String?, val unitLong: String?)

data class Us(val amount: Number?, val unitShort: String?, val unitLong: String?)

data class WinePairing(
    val pairedWines: List<Any>?,
    val pairingText: String?,
    val productMatches: List<Any>?
)
