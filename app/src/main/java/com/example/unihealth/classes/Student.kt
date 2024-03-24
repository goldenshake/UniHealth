package com.example.unihealth.classes

import com.google.firebase.firestore.Exclude

class Student(val fname: String, val lname: String, val email: String) {
    constructor() : this("", "", "")

    @Exclude
    var id: String = ""
}