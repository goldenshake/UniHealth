package com.example.unihealth

data class BodyWorkoutPlan(
    val name: String,
    val image: Int,
    val exercises: List<String>,
    val durationInMinutes: Int,
    val tags: List<String>
)