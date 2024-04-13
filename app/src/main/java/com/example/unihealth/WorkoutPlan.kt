package com.example.unihealth

import android.content.Intent
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
import com.google.android.material.appbar.MaterialToolbar

class WorkoutPlan : AppCompatActivity() {


    val workoutList = listOf(

//        Weight Loss

        BodyWorkoutPlan(
            "Weight Loss/General Fitness",
            R.drawable.running,
            listOf("Running"),
            30,
            "0",
            listOf("Weight Loss", "General Fitness")
        ),
        BodyWorkoutPlan(
            "Weight Loss/ General Fitness",
            R.drawable.cycling,
            listOf("Cycling"),
            60,

            "0",
            listOf("Weight Loss", "General Fitness")
        ),
        BodyWorkoutPlan(
            "Weight Loss/General Fitness",
            R.drawable.jumping_jack,
            listOf("Jumping Jacks"),
            15,

            "0",
            listOf("Weight Loss", "General Fitness")
        ),
        BodyWorkoutPlan(
            "Weight Loss",
            R.drawable.burpees,
            listOf("Burpees"),
            10,

            "0",
            listOf("Weight Loss")
        ),
        BodyWorkoutPlan(
            "Weight Loss/General Fitness",
            R.drawable.rowing,
            listOf("Rowing"),
            60,

            "0",
            listOf("Weight Loss", "General Fitness")
        ),
        BodyWorkoutPlan(
            "Weight Loss/General Fitness",
            R.drawable.jump_rope,
            listOf("Jump Rope"),
            30,

            "0",
            listOf("Weight Loss", "General Fitness")
        ),
        BodyWorkoutPlan(
            "Weight Loss",
            R.drawable.jump_squats,
            listOf("Squat Jumps"),
            30,

            "0",
            listOf("Weight Loss")
        ),
        BodyWorkoutPlan(
            "Weight Loss/General Fitness",
            R.drawable.hiking,
            listOf("Hiking"),
            50,

            "0",
            listOf("Weight Loss", "General Fitness")
        ),

//        Muscle Gain

        BodyWorkoutPlan(
            "Muscle Gain",
            R.drawable.deadlift,
            listOf("Deadlifts", "Back"),
            0,

            "4 sets x 8 reps",
            listOf("Muscle Gain")
        ),
        BodyWorkoutPlan(
            "Muscle Gain",
            R.drawable.squats,
            listOf("Squats", "Legs"),
            0,
            "4 sets x 12 reps",
            listOf("Muscle Gain")
        ),
        BodyWorkoutPlan(
            "Muscle Gain",
            R.drawable.bench_press,
            listOf("Bench Press", "Chest"),
            0,
            "4 sets x 10 reps",
            listOf("Muscle Gain")
        ),
        BodyWorkoutPlan(
            "Muscle Gain",
            R.drawable.pull_up,
            listOf("Pull-Ups", "Back"),
            0,

            "4 sets x 10 reps",
            listOf("Muscle Gain")
        ),
        BodyWorkoutPlan(
            "Muscle Gain",
            R.drawable.dumbbell_lunges,
            listOf("Dumbbell Lunges", "Legs"),
            0,

            "4 sets x 12 reps",
            listOf("Muscle Gain")
        ),
        BodyWorkoutPlan(
            "Muscle Gain",
            R.drawable.barbell_rows,
            listOf("Barbell Rows", "Back"),
            0,

            "4 sets x 12 reps",
            listOf("Muscle Gain")
        ),
        BodyWorkoutPlan(
            "Muscle Gain",
            R.drawable.leg_press,
            listOf("Leg Press", "Legs"),
            0,

            "4 sets x 12 reps",
            listOf("Muscle Gain")
        ),
        BodyWorkoutPlan(
            "Muscle Gain",
            R.drawable.shoulder_press,
            listOf("Shoulder Press", "Shoulders"),
            0,

            "4 sets x 12 reps",
            listOf("Muscle Gain")
        ),
        BodyWorkoutPlan(
            "Muscle Gain",
            R.drawable.dumbbell_curl,
            listOf("Dumbbell Curl", "Bicep"),
            0,

            "4 sets x 12 reps",
            listOf("Muscle Gain")
        ),
        BodyWorkoutPlan(
            "Muscle Gain",
            R.drawable.tricep_dips,
            listOf("Tricep Dips", "Tricep"),
            0,

            "4 sets x 12 reps",
            listOf("Muscle Gain")
        ),
        BodyWorkoutPlan(
            "Muscle Gain",
            R.drawable.leg_extension,
            listOf("Leg Extension", "Legs"),
            0,

            "4 sets x 12 reps",
            listOf("Muscle Gain")
        ),
        BodyWorkoutPlan(
            "Muscle Gain",
            R.drawable.barbell_curls,
            listOf("Barbell Curls", "Bicep"),
            0,

            "4 sets x 12 reps",
            listOf("Muscle Gain")
        ),
        BodyWorkoutPlan(
            "Muscle Gain",
            R.drawable.lateral_raise,
            listOf("Lateral Raise", "Shoulders"),
            0,

            "4 sets x 12 reps",
            listOf("Muscle Gain")
        ),
        BodyWorkoutPlan(
            "Muscle Gain",
            R.drawable.lat_pulldown,
            listOf("Lat Pull Downs", "Back"),
            0,

            "4 sets x 12 reps",
            listOf("Muscle Gain")
        ),


//        Endurance


        BodyWorkoutPlan(
            "Endurance",
            R.drawable.endurance,
            listOf("Long-Distance Running"),
            120,
            "0",
            listOf("Endurance")
        ),
        BodyWorkoutPlan(
            "Endurance",
            R.drawable.cycling2,
            listOf("Cycling"),
            60,
            "0",
            listOf("Endurance")
        ),
        BodyWorkoutPlan(
            "Endurance",
            R.drawable.swimming,
            listOf("Swimming"),
            60,
            "0",
            listOf("Endurance")
        ),
        BodyWorkoutPlan(
            "Endurance",
            R.drawable.rowing2,
            listOf("Rowing"),
            30,
            "0",
            listOf("Endurance")
        ),
        BodyWorkoutPlan(
            "Endurance",
            R.drawable.jump_rope2,
            listOf("Jump Rope"),
            25,
            "0",
            listOf("Endurance")
        ),
        BodyWorkoutPlan(
            "Endurance",
            R.drawable.stair_climbing,
            listOf("Stair Climbing"),
            30,
            "0",
            listOf("Endurance")
        ),
        BodyWorkoutPlan(
            "Endurance",
            R.drawable.boxjump,
            listOf("Box Jumps"),
            23,
            "0",
            listOf("Endurance")
        ),
        BodyWorkoutPlan(
            "Endurance",
            R.drawable.battle_ropes,
            listOf("Battle Ropes"),
            15,
            "0",
            listOf("Endurance")
        ),

//        Flexibility

        BodyWorkoutPlan(
            "Flexibility",
            R.drawable.butterfly_stretch,
            listOf("Butterfly Stretch"),
            30,
            "0",
            listOf("Flexibility")
        ),
        BodyWorkoutPlan(
            "Flexibility",
            R.drawable.hamstring_stretch,
            listOf("Hamstring Stretch"),
            15,
            "0",
            listOf("Flexibility")
        ),
        BodyWorkoutPlan(
            "Flexibility",
            R.drawable.foam_rolling,
            listOf("Foam Rolling"),
            15,
            "0",
            listOf("Flexibility")
        ),
        BodyWorkoutPlan(
            "Flexibility",
            R.drawable.stretching,
            listOf("Dynamic Stretching"),
            15,
            "0",
            listOf("Flexibility")
        ),


//        General Fitness


        BodyWorkoutPlan(
            "General Fitness",
            R.drawable.plank,
            listOf("Plank"),
            10,
            "0",
            listOf("General Fitness")
        ),
        BodyWorkoutPlan(
            "General Fitness",
            R.drawable.pushup,
            listOf("Push Ups"),
            0,
            "4 sets x 8 reps",
            listOf("General Fitness")
        ),
        BodyWorkoutPlan(
            "General Fitness",
            R.drawable.situp,
            listOf("Sit-Ups"),
            0,
            "6 sets 8 reps",
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
        val workoutAdapter = WorkoutAdapter(workoutList.toMutableList(), supportFragmentManager)
        recyclerView.adapter = workoutAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        val topAppBar: MaterialToolbar = findViewById(R.id.topAppBar)
        val menu = topAppBar.menu
        val nameItem = menu.findItem(R.id.name)

        topAppBar.setNavigationOnClickListener {
            // Handle navigation icon press
            finish()
        }

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