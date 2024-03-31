package com.example.unihealth.classes

import com.google.firebase.Timestamp

data class Reply(

    val name: String,
    val email: String,
    val message: String,
    val timestamp: Any
){
    constructor() : this("", "","",  Timestamp.now())
}