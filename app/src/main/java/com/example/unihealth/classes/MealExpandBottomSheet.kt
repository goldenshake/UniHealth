package com.example.unihealth.classes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.unihealth.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Locale

class MealExpandBottomSheet : BottomSheetDialogFragment() {
    private lateinit var bottomsheetName: TextView
    private lateinit var bottomsheetIngredients: TextView
    private lateinit var bottomsheetCalories: TextView
    private lateinit var bottomsheetImage: ImageView
    private lateinit var bottomsheetCategory_icon1: ImageView
    private lateinit var bottomsheetCategory_icon2: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.bottomsheetmealplan, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomsheetName = view.findViewById(R.id.bottomsheetName)
        bottomsheetIngredients = view.findViewById(R.id.bottomsheetIngredients)
        bottomsheetCalories = view.findViewById(R.id.bottomsheetCalories)
        bottomsheetImage = view.findViewById(R.id.bottomsheetImage)
        bottomsheetCategory_icon1 = view.findViewById(R.id.bottomsheetCategory_icon1)
        bottomsheetCategory_icon2 = view.findViewById(R.id.bottomsheetCategory_icon2)

        // Retrieve data from arguments
        val name = arguments?.getString("name") ?: ""
        val ingredients = arguments?.getString("ingredients") ?: ""
        val calories = arguments?.getString("calories") ?: ""
        val category_icon_1_id = arguments?.getInt("category_icon_1_id") ?: ""
        val category_icon_2_id = arguments?.getInt("category_icon_2_id") ?: ""
        if (category_icon_2_id == 0) {
            bottomsheetCategory_icon2.visibility = View.GONE
        }else{
            bottomsheetCategory_icon2.visibility = View.VISIBLE
        }
        // Set data to views
        bottomsheetName.text = name
        bottomsheetIngredients.text = ingredients
        bottomsheetCalories.text = calories

        bottomsheetCategory_icon1.setImageResource(category_icon_1_id as Int)
        bottomsheetCategory_icon2.setImageResource(category_icon_2_id as Int)
        val drawableResId = context?.resources?.getIdentifier(
            name.lowercase(Locale.ROOT).replace(" ", "_"), "drawable", requireContext().packageName
        )
        if (drawableResId != null) {
            bottomsheetImage.setImageResource(drawableResId)
        }
    }

    fun setData(
        name: String,
        ingredients: String,
        calories: String
    ) {

        if (::bottomsheetName.isInitialized &&
            ::bottomsheetIngredients.isInitialized &&
            ::bottomsheetCalories.isInitialized
        ) {
            bottomsheetName.text = name
            bottomsheetIngredients.text = ingredients
            bottomsheetCalories.text = calories
        } else {
            Log.e(TAG, "Views are not initialized")
        }

    }

    companion object {

        const val TAG = "ModalBottomSheet"

        fun newInstance(
            name: String,
            ingredients: String,
            calories: String,
            cat1: Int,
            cat2: Int
        ): MealExpandBottomSheet {
            val fragment = MealExpandBottomSheet()
            val args = Bundle().apply {
                putString("name", name)
                putString("ingredients", ingredients)
                putString("calories", calories)
                putInt("category_icon_1_id", cat1)
                putInt("category_icon_2_id", cat2)
            }
            fragment.arguments = args
            return fragment
        }
    }


}