package com.example.unihealth

data class RecipeMealPlans(
    val name: String,
    val image: Int, // You can use resource IDs here
    val ingredients: List<String>,
    val calories: Int,
    val categories: List<String>
)