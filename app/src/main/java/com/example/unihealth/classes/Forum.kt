package com.example.unihealth.classes

import com.google.firebase.Timestamp

data class Forum(
    val id: String,
    val name: String,
    val email: String,
    val title: String,
    val description: String,
    val timestamp: Any,
    val replies: List<Reply> = emptyList(),
    val edited: Boolean
) {
    constructor() : this("", "", "", "", "", Timestamp.now(), emptyList(), false)

}