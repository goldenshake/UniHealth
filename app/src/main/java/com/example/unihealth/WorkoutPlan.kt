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

//        Weight Loss

        BodyWorkoutPlan(
            "Weight Loss/General Fitness",
            R.drawable.healthcare,
            listOf("Running"),
            30,
            listOf("Weight Loss", "General Fitness")
        ),
        BodyWorkoutPlan(
            "Weight Loss/ General Fitness",
            R.drawable.healthcare,
            listOf("Cycling"),
            30,
            listOf("Weight Loss", "General Fitness")
        ),
        BodyWorkoutPlan(
            "Weight Loss/General Fitness",
            R.drawable.healthcare,
            listOf("Jumping Jacks"),
            30,
            listOf("Weight Loss", "General Fitness")
        ),
        BodyWorkoutPlan(
            "Weight Loss",
            R.drawable.healthcare,
            listOf("Burpees"),
            30,
            listOf("Weight Loss")
        ),
        BodyWorkoutPlan(
            "Weight Loss/General Fitness",
            R.drawable.healthcare,
            listOf("Rowing"),
            30,
            listOf("Weight Loss", "General Fitness")
        ),
        BodyWorkoutPlan(
            "Weight Loss/General Fitness",
            R.drawable.healthcare,
            listOf("Jump Rope"),
            30,
            listOf("Weight Loss", "General Fitness")
        ),
        BodyWorkoutPlan(
            "Weight Loss",
            R.drawable.healthcare,
            listOf("Squat Jumps"),
            30,
            listOf("Weight Loss")
        ),
        BodyWorkoutPlan(
            "Weight Loss/General Fitness",
            R.drawable.healthcare,
            listOf("Hiking"),
            30,
            listOf("Weight Loss", "General Fitness")
        ),

//        Muscle Gain

        BodyWorkoutPlan(
            "Muscle Gain",
            R.drawable.healthcare,
            listOf("Deadlifts", "Back"),
            30,
            listOf("Muscle Gain")
        ),
        BodyWorkoutPlan(
            "Muscle Gain",
            R.drawable.healthcare,
            listOf("Squats", "Legs"),
            30,
            listOf("Muscle Gain")
        ),
        BodyWorkoutPlan(
            "Muscle Gain",
            R.drawable.healthcare,
            listOf("Bench Press", "Chest"),
            30,
            listOf("Muscle Gain")
        ),
        BodyWorkoutPlan(
            "Muscle Gain",
            R.drawable.healthcare,
            listOf("Pull-Ups", "Back"),
            30,
            listOf("Muscle Gain")
        ),
        BodyWorkoutPlan(
            "Muscle Gain",
            R.drawable.healthcare,
            listOf("Dumbbell Lunges", "Legs"),
            30,
            listOf("Muscle Gain")
        ),
        BodyWorkoutPlan(
            "Muscle Gain",
            R.drawable.healthcare,
            listOf("Barbell Rows", "Back"),
            30,
            listOf("Muscle Gain")
        ),
        BodyWorkoutPlan(
            "Muscle Gain",
            R.drawable.healthcare,
            listOf("Leg Press", "Legs"),
            30,
            listOf("Muscle Gain")
        ),
        BodyWorkoutPlan(
            "Muscle Gain",
            R.drawable.healthcare,
            listOf("Shoulder Press", "Shoulders"),
            30,
            listOf("Muscle Gain")
        ),
        BodyWorkoutPlan(
            "Muscle Gain",
            R.drawable.healthcare,
            listOf("Dumbbell Curl", "Bicep"),
            30,
            listOf("Muscle Gain")
        ),
        BodyWorkoutPlan(
            "Muscle Gain",
            R.drawable.healthcare,
            listOf("Tricep Dips", "Tricep"),
            30,
            listOf("Muscle Gain")
        ),
        BodyWorkoutPlan(
            "Muscle Gain",
            R.drawable.healthcare,
            listOf("Leg Extension", "Legs"),
            30,
            listOf("Muscle Gain")
        ),
        BodyWorkoutPlan(
            "Muscle Gain",
            R.drawable.healthcare,
            listOf("Barbell Curls", "Bicep"),
            30,
            listOf("Muscle Gain")
        ),
        BodyWorkoutPlan(
            "Muscle Gain",
            R.drawable.healthcare,
            listOf("Lateral Raise", "Shoulders"),
            30,
            listOf("Muscle Gain")
        ),
        BodyWorkoutPlan(
            "Muscle Gain",
            R.drawable.healthcare,
            listOf("Lat Pull Downs", "Back"),
            30,
            listOf("Muscle Gain")
        ),


//        Endurance


        BodyWorkoutPlan(
            "Endurance",
            R.drawable.healthcare,
            listOf("Long-Distance Running"),
            30,
            listOf("Endurance")
        ),
        BodyWorkoutPlan(
            "Endurance",
            R.drawable.healthcare,
            listOf("Cycling"),
            30,
            listOf("Endurance")
        ),
        BodyWorkoutPlan(
            "Endurance",
            R.drawable.healthcare,
            listOf("Swimming"),
            30,
            listOf("Endurance")
        ),
        BodyWorkoutPlan(
            "Endurance",
            R.drawable.healthcare,
            listOf("Rowing"),
            30,
            listOf("Endurance")
        ),
        BodyWorkoutPlan(
            "Endurance",
            R.drawable.healthcare,
            listOf("Jump Rope"),
            30,
            listOf("Endurance")
        ),
        BodyWorkoutPlan(
            "Endurance",
            R.drawable.healthcare,
            listOf("Stair Climbing"),
            30,
            listOf("Endurance")
        ),
        BodyWorkoutPlan(
            "Endurance",
            R.drawable.healthcare,
            listOf("Box Jumps"),
            30,
            listOf("Endurance")
        ),
        BodyWorkoutPlan(
            "Endurance",
            R.drawable.healthcare,
            listOf("Battle Ropes"),
            30,
            listOf("Endurance")
        ),

//        Flexibility

        BodyWorkoutPlan(
            "Flexibility",
            R.drawable.healthcare,
            listOf("Butterfly Stretch"),
            30,
            listOf("Flexibility")
        ),
        BodyWorkoutPlan(
            "Flexibility",
            R.drawable.healthcare,
            listOf("Hamstring Stretch"),
            30,
            listOf("Flexibility")
        ),
        BodyWorkoutPlan(
            "Flexibility",
            R.drawable.healthcare,
            listOf("Foam Rolling"),
            30,
            listOf("Flexibility")
        ),
        BodyWorkoutPlan(
            "Flexibility",
            R.drawable.healthcare,
            listOf("Dynamic Stretching"),
            30,
            listOf("Flexibility")
        ),


//        General Fitness


        BodyWorkoutPlan(
            "General Fitness",
            R.drawable.healthcare,
            listOf("Plank"),
            30,
            listOf("General Fitness")
        ),
        BodyWorkoutPlan(
            "General Fitness",
            R.drawable.healthcare,
            listOf("Push Ups"),
            30,
            listOf("General Fitness")
        ),
        BodyWorkoutPlan(
            "General Fitness",
            R.drawable.healthcare,
            listOf("Sit-Ups"),
            30,
            listOf("General Fitness")
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