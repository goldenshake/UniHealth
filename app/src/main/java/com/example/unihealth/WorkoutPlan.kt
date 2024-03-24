package com.example.unihealth

import android.os.Bundle
import android.widget.CheckBox
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unihealth.classes.BodyWorkoutPlan
import com.example.unihealth.classes.WorkoutAdapter

class WorkoutPlan : AppCompatActivity() {

    val workoutList = listOf(
        BodyWorkoutPlan(
            "Cardio",
            R.drawable.healthcare,
            listOf("Running", "Jumping Jacks", "Burpees"),
            30,
            listOf("Endurance", "Weight Loss")
        ),
        BodyWorkoutPlan(
            "Strength Training",
            R.drawable.healthcare,
            listOf("Squats", "Push-ups", "Deadlifts"),
            45,
            listOf("Muscle Gain", "Strength")
        ),
        BodyWorkoutPlan(
            "Yoga",
            R.drawable.healthcare,
            listOf("Sun Salutation", "Warrior Pose", "Downward Dog"),
            60,
            listOf("Flexibility", "General Fitness")
        ),
        BodyWorkoutPlan(
            "HIIT",
            R.drawable.healthcare,
            listOf("High Knees", "Mountain Climbers", "Plank Jacks"),
            40,
            listOf("Endurance", "Weight Loss")
        ),
        BodyWorkoutPlan(
            "Circuit Training",
            R.drawable.healthcare,
            listOf("Jump Rope", "Box Jumps", "Bench Press"),
            50,
            listOf("Muscle Gain", "General Fitness")
        ),
        BodyWorkoutPlan(
            "Pilates",
            R.drawable.healthcare,
            listOf("Hundreds", "Leg Circles", "Criss-Cross"),
            45,
            listOf("Flexibility", "Core Strength")
        ),
        BodyWorkoutPlan(
            "Swimming",
            R.drawable.healthcare,
            listOf("Freestyle", "Breaststroke", "Butterfly"),
            45,
            listOf("Endurance", "General Fitness")
        ),
        BodyWorkoutPlan(
            "Cycling",
            R.drawable.healthcare,
            listOf("Indoor Cycling", "Outdoor Cycling", "Sprints"),
            60,
            listOf("Endurance", "Weight Loss")
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_workoutplan)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val recyclerView = findViewById<RecyclerView>(R.id.workoutRecyclerView)
        val workoutAdapter = WorkoutAdapter(workoutList.toMutableList())
        recyclerView.adapter = workoutAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        val weightLossCheckbox = findViewById<CheckBox>(R.id.weightLossCheckbox)
        val muscleGainCheckbox = findViewById<CheckBox>(R.id.muscleGainCheckbox)
        val enduranceCheckbox = findViewById<CheckBox>(R.id.enduranceCheckbox)
        val flexibilityCheckbox = findViewById<CheckBox>(R.id.flexibilityCheckbox)
        val genFitnessCheckbox = findViewById<CheckBox>(R.id.genFitnessCheckbox)

        val checkboxes = listOf(
            weightLossCheckbox,
            muscleGainCheckbox,
            enduranceCheckbox,
            flexibilityCheckbox,
            genFitnessCheckbox
        )

        for (checkbox in checkboxes) {
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                val selectedCategories = checkboxes
                    .filter { it.isChecked }
                    .map { it.text.toString() }
                val filteredworkouts = if (selectedCategories.isNotEmpty()) {
                    workoutList.filter { meal ->
                        meal.tags.any { it in selectedCategories }
                    }
                } else {
                    workoutList
                }
                workoutAdapter.updateList(filteredworkouts)
            }

        }
    }
}