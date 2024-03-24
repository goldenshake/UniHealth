package com.example.unihealth

import com.example.unihealth.classes.MealAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.CheckBox
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unihealth.classes.RecipeMealPlans

class MealPlans : AppCompatActivity() {

    private lateinit var mealAdapter: MealAdapter

    val mealList = listOf(
        RecipeMealPlans(
            "Salad",
            R.drawable.salad,
            listOf("Lettuce", "Tomato", "Cucumber"),
            150,
            listOf("Vegetarian", "Low Carbs")
        ),
        RecipeMealPlans(
            "Pasta",
            R.drawable.spaguetti,
            listOf("Pasta", "Tomato Sauce", "Cheese"),
            300,
            listOf("Dairy-Free")
        ),

        )

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

        var filteredMeals: List<RecipeMealPlans> = mealList
        val recyclerView = findViewById<RecyclerView>(R.id.mealRecyclerView)
        val mealAdapter = MealAdapter(mealList.toMutableList())
        recyclerView.adapter = mealAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

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
                    mealList.filter { meal ->
                        meal.categories.any { it in selectedCategories }
                    }
                } else {
                    mealList
                }
                mealAdapter.updateList(filteredMeals)
            }

        }
    }
}