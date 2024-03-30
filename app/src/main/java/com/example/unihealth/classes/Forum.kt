package com.example.unihealth.classes

import com.google.firebase.firestore.Exclude

class Forum(val name: String, val email: String, val title: String, val description: String) {
    constructor() : this("", "", "", "")

    @Exclude
    var id: String = ""
}