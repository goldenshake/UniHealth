package com.example.unihealth.classes

data class BodyWorkoutPlan(
    val name: String,
    val image: Int,
    val exercises: List<String>,
    val durationInMinutes: Int,
    val sets: String,
    val tags: List<String>


){
    constructor() : this("", 0, emptyList(), 0,"0", emptyList())
}