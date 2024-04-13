package com.example.unihealth.classes

data class RecipeMealPlansDB(
    val name: String,
    val ingredients: List<String>,
    val ingredientsMax: List<String>,
    val calories: Int,
    val categories: List<String>
) {

    constructor() : this("", emptyList(), emptyList(), 0, emptyList())
}