package com.example.unihealth.classes

import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude


data class Reply(
    val userName: String,
    val message: String,
    val timestamp: Any
)

data class Forum(
    val name: String,
    val email: String,
    val title: String,
    val description: String,
    val timestamp: Any,
    val replies: List<Reply> = emptyList()
) {
    constructor() : this("", "", "", "", Timestamp.now())

    @Exclude
    var id: String = ""
}