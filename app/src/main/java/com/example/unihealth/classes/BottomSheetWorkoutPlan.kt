package com.example.unihealth.classes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.unihealth.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetWorkoutPlan : BottomSheetDialogFragment() {
    private lateinit var bottomsheetName: TextView
    private lateinit var bottomsheetSets: TextView
    private lateinit var bottomsheetClock: ImageView
    private lateinit var bottomsheetMinutes: TextView
    private lateinit var bottomsheetImage: ImageView
    private lateinit var bottomsheetCategory_icon1: ImageView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.bottomsheetworkoutplan, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Retrieve data from arguments

        bottomsheetName = view.findViewById(R.id.bottomsheetName)
        bottomsheetSets = view.findViewById(R.id.bottomsheetSets)
        bottomsheetClock = view.findViewById(R.id.imageView4)
        bottomsheetMinutes = view.findViewById(R.id.bottomsheetMinutes)
        bottomsheetImage = view.findViewById(R.id.bottomsheetImage)
        bottomsheetCategory_icon1 = view.findViewById(R.id.bottomsheetCategory_icon1)

        val name = arguments?.getString("name") ?: ""
        val image = arguments?.getInt("image") ?: 0
        val cat1 = arguments?.getInt("cat1") ?: 0
        val duration = arguments?.getInt("duration") ?: 0
        val sets = arguments?.getString("sets") ?: ""


        bottomsheetName.text = name
        bottomsheetSets.text = sets
        bottomsheetMinutes.text = "${duration} mins"
        bottomsheetImage.setImageResource(image)
        bottomsheetCategory_icon1.setImageResource(cat1)

        if(sets == "0"){
            bottomsheetSets.visibility = View.GONE
        }else{
            bottomsheetSets.visibility = View.VISIBLE
        }

        if(duration == 0){
            bottomsheetClock.visibility = View.GONE
            bottomsheetMinutes.visibility = View.GONE
        }else{
            bottomsheetClock.visibility = View.VISIBLE
            bottomsheetMinutes.visibility = View.VISIBLE
        }
//        val category_icon_1_id = arguments?.getInt("category_icon_1_id") ?: ""

    }


    companion object {
        const val TAG = "ModalBottomSheet"
        fun newInstance(
            name: String,
            image: Int,
            duration: Int,
            sets: String,
            cat1:Int
        ): BottomSheetWorkoutPlan {
            val fragment = BottomSheetWorkoutPlan()
            val args = Bundle().apply {
                putString("name", name)
                putInt("image", image)
                putInt("duration", duration)
                putString("sets", sets)
                putInt("cat1", cat1)
            }
            fragment.arguments = args
            return fragment
        }
    }


}