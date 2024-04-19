package com.example.unihealth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.unihealth.classes.ChallengesDB
import com.example.unihealth.classes.Constants
import com.example.unihealth.classes.Student
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class signup : AppCompatActivity() {

    private lateinit var userAuth: FirebaseAuth
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val student_collection_ref: CollectionReference = db.collection("student")
    private val student_challenge_collection_ref: CollectionReference = db.collection("challenge")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        userAuth = FirebaseAuth.getInstance()
        val editTextInputLayoutFName = findViewById<TextInputLayout>(R.id.fNameSignUpText)
        val editTextInputLayoutLName = findViewById<TextInputLayout>(R.id.lNameSignUpText)
        val editTextInputLayoutEmail = findViewById<TextInputLayout>(R.id.emailSignUpText)
        val editTextInputLayoutPassword = findViewById<TextInputLayout>(R.id.passwordSignUpText)
        val editTextInputLayoutConfirmPassword =
            findViewById<TextInputLayout>(R.id.confirmpasswordSignUpText)
        val buttonSignUp = findViewById<Button>(R.id.buttonSignUp)
        buttonSignUp.setOnClickListener {


            val fname = editTextInputLayoutFName.editText?.text.toString().trim()
            val lname = editTextInputLayoutLName.editText?.text.toString().trim()
            val email = editTextInputLayoutEmail.editText?.text.toString().trim()
            val password = editTextInputLayoutPassword.editText?.text.toString().trim()
            val confirmpassword =
                editTextInputLayoutConfirmPassword.editText?.text.toString().trim()

            if (fname.isEmpty() || lname.isEmpty() || email.isEmpty() || password.isEmpty() || confirmpassword.isEmpty()) {
                Toast.makeText(
                    this@signup,
                    "All fields are required",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                userAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this@signup,
                                "Sign up successful",
                                Toast.LENGTH_SHORT
                            ).show()
                            // Navigate to the next activity or perform any other action
                            val student = Student(fname, lname, email)
                            student_collection_ref.add(student)
                            userAuth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        // Proceed to the next activity or perform any other action
                                        val intent = Intent(this, MainHome::class.java)
                                        intent.putExtra(Constants.INTENT_EMAIL, email)
                                        startActivity(intent)

                                    }
                                }
                        } else {
                            Toast.makeText(
                                this@signup,
                                "Sign up failed: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }

        }
    }
}


