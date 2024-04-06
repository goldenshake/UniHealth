package com.example.unihealth

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unihealth.classes.ChallengesAdapter
import com.example.unihealth.classes.Constants
import com.example.unihealth.classes.challengePlan

class challenges : AppCompatActivity() {

    val challengesList = listOf(
        challengePlan(
            "Hot Stepper",
            "Make a total of 1000 steps",
            "Easy"
        ),
        challengePlan(
            "Hot Stepper1",
            "Make a total of 1000 steps",
            "Easy"
        ),
        challengePlan(
            "Hot Stepper2",
            "Make a total of 1000 steps",
            "Easy"
        ),
        challengePlan(
            "Hot Stepper3",
            "Make a total of 1000 steps",
            "Easy"
        ),
        challengePlan(
            "Hot Stepper4",
            "Make a total of 1000 steps",
            "Easy"
        ),
        challengePlan(
            "Hot Stepper5",
            "Make a total of 1000 steps",
            "Easy"
        ),
        challengePlan(
            "Hot Stepper6",
            "Make a total of 1000 steps",
            "Easy"
        ),
        challengePlan(
            "Hot Stepper7",
            "Make a total of 1000 steps",
            "Easy"
        ),
        challengePlan(
            "Hot Stepper8",
            "Make a total of 1000 steps",
            "Easy"
        ),
        challengePlan(
            "Hot Stepper9",
            "Make a total of 1000 steps",
            "Easy"
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
    }
}