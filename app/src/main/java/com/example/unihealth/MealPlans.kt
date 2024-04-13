package com.example.unihealth

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unihealth.classes.MealAdapter
import com.example.unihealth.classes.RecipeMealPlansDB
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class MealPlans : AppCompatActivity() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val meal_collection_ref: CollectionReference = db.collection("meal")


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_meal_plans)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        val mealItemsList = mutableListOf<RecipeMealPlansDB>()
        val recyclerView = findViewById<RecyclerView>(R.id.mealRecyclerView)
        val mealAdapter = MealAdapter(mutableListOf(), this, this, supportFragmentManager)
        recyclerView.adapter = mealAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        val topAppBar: MaterialToolbar = findViewById(R.id.topAppBar)
        val menu = topAppBar.menu
        val nameItem = menu.findItem(R.id.name)

        topAppBar.setNavigationOnClickListener {
            // Handle navigation icon press
            finish()
        }

        meal_collection_ref.orderBy("name", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val mealItem = document.toObject(RecipeMealPlansDB::class.java)
                    mealItemsList.add(mealItem)
                }
                // Update adapter with the retrieved data
                mealAdapter.updateList(mealItemsList)
            }
            .addOnFailureListener { exception ->
                // Handle any errors
                Log.e(TAG, "Error getting documents: ", exception)
            }


        val vegetarianCheckbox = findViewById<CheckBox>(R.id.vegetarianCheckbox)
        val dairyFreeCheckbox = findViewById<CheckBox>(R.id.dairyFreeCheckbox)
        val lowCarbsCheckbox = findViewById<CheckBox>(R.id.lowCarbsCheckbox)
        val nutFreeCheckbox = findViewById<CheckBox>(R.id.nutFreeCheckbox)
        val veganCheckbox = findViewById<CheckBox>(R.id.veganCheckbox)


        val checkboxes = listOf(
            vegetarianCheckbox,
            dairyFreeCheckbox,
            lowCarbsCheckbox,
            nutFreeCheckbox,
            veganCheckbox
        )

        for (checkbox in checkboxes) {
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                val selectedCategories = checkboxes
                    .filter { it.isChecked }
                    .map { it.text.toString() }
                val filteredMeals = if (selectedCategories.isNotEmpty()) {
                    mealItemsList.filter { meal ->
                        meal.categories.any { it in selectedCategories }
                    }
                } else {
                    mealItemsList
                }
                mealAdapter.updateList(filteredMeals)
            }

        }
    }
}