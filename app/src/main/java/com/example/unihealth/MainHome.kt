package com.example.unihealth

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2

class MainHome : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var previousButton: Button
    private lateinit var nextButton: Button
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

        // Set up ViewPager2 adapter
        val adapter = RecipeAdapter(recipes)
        viewPager.adapter = adapter

        // Handle button clicks
        previousButton.setOnClickListener {
            viewPager.currentItem -= 1
        }

        nextButton.setOnClickListener {
            viewPager.currentItem += 1
        }

    }
}