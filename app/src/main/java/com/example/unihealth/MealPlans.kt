package com.example.unihealth

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unihealth.classes.MealAdapter
import com.example.unihealth.classes.RecipeMealPlans

class MealPlans : AppCompatActivity() {

    private lateinit var mealAdapter: MealAdapter

    val mealList = listOf(
        RecipeMealPlans(
            "Spicy Tofu Scramble",
            R.drawable.salad,
            listOf("Saute Crumbled tofu, Chopped Onions, Bell peppers, Chili Powder, Vegetable Broth"),
            300,
            listOf("Vegan", "Low Carbs")
        ),
        RecipeMealPlans(
            "Black Bean Burger with avocado Cream",
            R.drawable.spaguetti,
            listOf("Black Beans, Zucchini, Bell Pepper, Flaxseed, Chili Powder, Smked Parika, Avocado, Lettuce Wrap"),
            400,
            listOf("Vegetarian", "Low Carbs")
        ),
        RecipeMealPlans(
            "Hemp Seed and Chia Pudding(with Berries)",
            R.drawable.spaguetti,
            listOf(
                "Hemp Seeds",
                "Chia Seeds",
                "Almond Milk",
                "Coconut Milk",
                "Berries(fresh or frozen)"
            ),
            300,
            listOf("Vegan", "Low Carbs")
        ),
        RecipeMealPlans(
            "Stir-Fried Tofu with Vegetables",
            R.drawable.spaguetti,
            listOf(
                "Firm tofu, cubed",
                "Broccoli florets",
                "Sliced bell peppers (red, yellow, and green)",
                "Sliced carrots",
                "Minced garlic",
                "Low-sodium soy sauce",
                "Sesame oil",
                "Red pepper flakes (optional)"
            ),
            250,
            listOf("Vegan", "Low Carbs")
        ),
        RecipeMealPlans(
            "Cauliflower Rice Stir-Fry",
            R.drawable.spaguetti,
            listOf(
                "Cauliflower, grated into rice-like pieces",
                "Diced tofu",
                "Diced zucchini",
                "Diced bell peppers (any color)",
                "Chopped onions",
                "Minced ginger",
                "Low-sodium soy sauce",
                "Sesame oil",
                "Chopped green onions (for garnish)"
            ),
            280,
            listOf("Vegan", "Low Carbs")
        ),
        RecipeMealPlans(
            "Vegan Lentil Salad",
            R.drawable.spaguetti,
            listOf(
                "Cooked green lentils",
                "Diced cucumber",
                "Cherry tomatoes, halved",
                "Chopped red onion",
                "Chopped fresh parsley",
                "Lemon juice",
                "Extra virgin olive oil",
                "Dijon mustard",
                "Salt and pepper"
            ),
            270,
            listOf("Vegan", "Low Carbs")
        ),
        RecipeMealPlans(
            "Grilled Chicken Salad",
            R.drawable.spaguetti,
            listOf(
                "Grilled chicken breast",
                "Mixed salad greens (lettuce, spinach, arugula)",
                "Cherry tomatoes",
                "Cucumber",
                "Red onion",
                "Balsamic vinaigrette dressing"
            ),
            350,
            listOf("Dairy Free")
        ),
        RecipeMealPlans(
            "Stir-Fried Tofu with Vegetables",
            R.drawable.spaguetti,
            listOf(
                "Firm tofu, cubed",
                        "Broccoli florets",
                        "Bell peppers(red, green, yellow)",
                        "Snow peas",
                        "Carrots, julienned",
                        "Garlic, minced",
                        "Soy sauce",
                        "Sesame oil",
                        "Brown rice(optional, for serving)"
            ),
            300,
            listOf("Dairy Free")
        ),
        RecipeMealPlans(
            "Turkey and Vegetable Quinoa Bowl",
            R.drawable.spaguetti,
            listOf("Lean ground turkey",
                    "Quinoa, cooked",
                    "Zucchini, diced",
                    "Yellow squash, diced",
                    "Red bell pepper, diced",
                    "Onion, diced",
                    "Garlic, minced",
                    "Olive oil",
                    "Salt and pepper to taste",
                    "Fresh parsley for garnish"),
            400,
            listOf("Dairy Free")
        ),
        RecipeMealPlans(
            "Salmon with Roasted Vegetables",
            R.drawable.spaguetti,
            listOf("Salmon fillets",
                    "Asparagus spears",
                    "Brussels sprouts, halved",
                    "Cherry tomatoes",
                    "Red onion, sliced",
                    "Olive oil",
                    "Lemon juice",
                    "Garlic powder",
                    "Paprika",
                    "Salt and pepper to taste"),
            380,
            listOf("Dairy Free")
        )

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
        val emptyFind = findViewById<TextView>(R.id.emptyFind)


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