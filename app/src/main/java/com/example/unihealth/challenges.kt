package com.example.unihealth

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unihealth.classes.ChallengesAdapter
import com.example.unihealth.classes.Constants
import com.example.unihealth.classes.challengePlan
import com.google.android.material.appbar.MaterialToolbar

class challenges : AppCompatActivity() {

    val challengesList = listOf(
        challengePlan(
            "Hot Stepper",
            "Make a total of 1000 steps",
            "Easy"
        ),
        challengePlan(
            "Push Up Beginner",
            "Do 50 push ups in a row",
            "Medium"
        ),
        challengePlan(
            "Plannnk?",
            "Do a plank for 3 minutes",
            "Hard"
        ),
        challengePlan(
            "High Stamina",
            "Run 5 miles",
            "Medium"
        ),
        challengePlan(
            "Pull- Up Challenge",
            "Do 10 pull ups",
            "Medium"
        ),
        challengePlan(
            "Bench Master 1 ",
            "Bench press a total of 80 pounds",
            "Easy"
        ),
        challengePlan(
            "UnAlive Lift",
            "Deadlift 135 pounds",
            "Medium"
        ),
        challengePlan(
            "Squat Master 1",
            "Squat a total of 135 pounds",
            "Medium"
        ),
        challengePlan(
            "Bench Master 2",
            "Bench press a total of 135 pounds",
            "Hard"
        ),
        challengePlan(
            "Squat Master 2",
            "Squat a total of 200 pounds",
            "Hard"
        )
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_challenges)

        val intentData = intent.extras
        val email = intentData?.getString(Constants.INTENT_EMAIL)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewChallenges)
        val adapter = ChallengesAdapter(challengesList.toMutableList(), email.toString())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        val topAppBar: MaterialToolbar = findViewById(R.id.topAppBar)
        val menu = topAppBar.menu
        val nameItem = menu.findItem(R.id.name)

        topAppBar.setNavigationOnClickListener {
            // Handle navigation icon press
            finish()
        }
    }
}