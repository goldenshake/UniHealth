package com.example.unihealth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var userAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val editTextInputLayoutEmail = findViewById<TextInputLayout>(R.id.loginEmail)
        val editTextInputLayoutPassword = findViewById<TextInputLayout>(R.id.loginPassword)
        userAuth = FirebaseAuth.getInstance()
        val loginButton = findViewById<Button>(R.id.loginButton)
        val signUpText = findViewById<TextView>(R.id.signUpText)

        signUpText.setOnClickListener {
            val intent = Intent(this, signup::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            val email = editTextInputLayoutEmail.editText?.text.toString().trim()
            val password = editTextInputLayoutPassword.editText?.text.toString().trim()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this@MainActivity,
                    "Please enter email and password",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                userAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this@MainActivity,
                                "Login successful",
                                Toast.LENGTH_SHORT
                            ).show()
                            // Proceed to the next activity or perform any other action
                            val intent = Intent(this, MainHome::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                "Login failed: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

            }
        }
    }
}