package hu.ait.androidfinal.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import hu.ait.androidfinal.R
import hu.ait.androidfinal.data.Ingredient
import kotlinx.android.synthetic.main.new_pantry_item_dialog.view.*
import java.lang.RuntimeException

class NewPantryItemDialog : DialogFragment() {


    interface ItemHandler {
        fun itemCreated(pantryItem: Ingredient)
        fun itemUpdated(pantryItem: Ingredient)
    }

    private lateinit var itemHandler: ItemHandler

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is ItemHandler) {
            itemHandler = context
        }
        else {
            throw RuntimeException(
                "The activity does not implement to ItemHandler Interface"
            )
        }
    }

    private lateinit var etItemName: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle("New Pantry Item")

        val rootView = requireActivity().layoutInflater.inflate(
            R.layout.new_pantry_item_dialog, null
        )
        etItemName = rootView.etNewItemName


        builder.setView(rootView)
        Log.d("type", "onCreate selI dial editText" + etItemName.getText().toString())



        builder.setPositiveButton("Add") {
                dialog, which -> // empty
        }

        return builder.create()

    }

    override fun onResume() {
        super.onResume()

        val positiveButton = (dialog as AlertDialog).getButton(Dialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener{
            if (etItemName.text.isNotEmpty()) {
                handleItemCreate()
                dialog!!.dismiss()
            } else {
                etItemName.error = getString(R.string.no_empty)
            }
        }
    }

    private fun handleItemCreate() {
        itemHandler.itemCreated(
            Ingredient(
                etItemName.text.toString(),
                0,
                "",
                0,
                false
            )
        )
    }
}