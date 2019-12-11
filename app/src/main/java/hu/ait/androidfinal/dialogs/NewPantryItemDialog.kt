package hu.ait.androidfinal.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import hu.ait.androidfinal.R
import hu.ait.androidfinal.data.Ingredient
import kotlinx.android.synthetic.main.new_pantry_item_dialog.*
import kotlinx.android.synthetic.main.new_pantry_item_dialog.view.*
import kotlinx.android.synthetic.main.new_pantry_item_dialog.view.spinnerUnit
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
    private lateinit var etItemQuantity: EditText
    private lateinit var spinnerUnit: Spinner
    private lateinit var spinnerType: Spinner

    //var isEditMode = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle("New Pantry Item")

        val rootView = requireActivity().layoutInflater.inflate(
            R.layout.new_pantry_item_dialog, null
        )
        etItemName = rootView.etNewItemName
        etItemQuantity = rootView.etNewItemQuant
        spinnerUnit = rootView.spinnerUnit
        spinnerType = rootView.spinnerType
        builder.setView(rootView)

        //isEditMode = ((arguments != null) && arguments!!.containsKey(ListActivity.KEY_ITEM))
        /**
        if (isEditMode) {
            builder.setTitle(getString(R.string.edit_item_title))
            var item = (arguments?.getSerializable(ListActivity.KEY_ITEM) as ShoppingItem)

            etItemName.setText(item.itemName)
            etItemDescription.setText(item.description)
            etItemPrice.setText(item.price.toString())
            spinnerType.setSelection(item.type)


        }
        **/

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
                //if (isEditMode){
                //    handleItemEdit()
                //}
                //else{
                handleItemCreate()
                //}
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
                spinnerType.selectedItemPosition,
                etItemQuantity.text.toString(), spinnerUnit.selectedItemPosition,
                false
            )
        )
    }
    /**
    private fun handleItemEdit() {
        val itemToEdit = arguments?.getSerializable(
            ListActivity.KEY_ITEM
        ) as ShoppingItem
        itemToEdit.itemName = etItemName.text.toString()
        itemToEdit.description = etItemDescription.text.toString()
        itemToEdit.price = etItemPrice.text.toString().toInt()
        itemToEdit.type = spinnerType.selectedItemPosition

        itemHandler.itemUpdated(itemToEdit)
    }
    **/
}