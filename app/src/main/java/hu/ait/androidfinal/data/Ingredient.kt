package hu.ait.androidfinal.data

data class Ingredient(
    var name : String? = "",
    var type : Int = 0,
    var quantity : String? = "",
    var unit : Int = 0,
    var include : Boolean = false
)