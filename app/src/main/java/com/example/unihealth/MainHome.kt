package com.example.unihealth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2

class MainHome : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var previousButton: Button
    private lateinit var nextButton: Button
    private lateinit var adapter: RecipeAdapter
    private lateinit var mealPlanCard: CardView
    private lateinit var workoutCard: CardView
    private lateinit var supportCard: CardView
    private lateinit var challengesCard: CardView
    private lateinit var waterCard: CardView
    private val recipes = listOf(
        Recipe("Recipe 1", R.drawable.ic_launcher_foreground),
        Recipe("Recipe 2", R.drawable.health_care),
        Recipe("Recipe 3", R.drawable.healthcare)
        // Add more recipes as needed
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewPager = findViewById(R.id.scollviewpager)
        previousButton = findViewById(R.id.previousButton)
        nextButton = findViewById(R.id.nextButton)
        mealPlanCard = findViewById(R.id.mealPlanCard)
        workoutCard = findViewById(R.id.workoutCard)
        supportCard = findViewById(R.id.supportCard)
        challengesCard = findViewById(R.id.challengesCard)
        waterCard = findViewById(R.id.waterIntakeCard)


        // Set up ViewPager2 adapter
        adapter = RecipeAdapter(recipes)
        viewPager.adapter = adapter

        // Handle button clicks
        previousButton.setOnClickListener {
            viewPager.currentItem -= 1
        }

        nextButton.setOnClickListener {
            viewPager.currentItem += 1
        }

        mealPlanCard.setOnClickListener {
            val intent = Intent(this, MealPlans::class.java)
            startActivity(intent)
        }
        workoutCard.setOnClickListener {
            val intent = Intent(this, WorkoutPlan::class.java)
            startActivity(intent)
        }
        supportCard.setOnClickListener {
            val intent = Intent(this, support::class.java)
            startActivity(intent)
        }
        challengesCard.setOnClickListener {
            val intent = Intent(this, challenges::class.java)
            startActivity(intent)
        }
        waterCard.setOnClickListener {
            val intent = Intent(this, waterIntake::class.java)
            startActivity(intent)
        }

    }
}