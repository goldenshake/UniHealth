package com.example.unihealth

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import com.example.unihealth.classes.Constants
import com.example.unihealth.classes.Recipe
import com.example.unihealth.classes.RecipeAdapter
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class MainHome : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var previousButton: Button
    private lateinit var nextButton: Button
    private lateinit var adapter: RecipeAdapter
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val student_collection_ref: CollectionReference = db.collection("student")
    private val studentChallengeCollectionRef: CollectionReference = db.collection("challenge")
    private lateinit var challengess: TextView
    private val recipes = listOf(
        Recipe("Christmas Salad", R.drawable.christmas_salad),
        Recipe("Homemade Fried Rice", R.drawable.homemade_fried_rice),
        Recipe("Pesto Spaghetti", R.drawable.pesto_spaghetti)
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        challengess = findViewById(R.id.challenges)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navigationView: NavigationView = findViewById(R.id.navigation_view)
        val topAppBar: MaterialToolbar = findViewById(R.id.topAppBar)
        viewPager = findViewById(R.id.scollviewpager)
        previousButton = findViewById(R.id.previousButton)
        nextButton = findViewById(R.id.nextButton)
        val intentData = intent.extras
        val menu = topAppBar.menu
        val nameItem = menu.findItem(R.id.name)

        topAppBar.setNavigationOnClickListener {
            // Handle navigation icon press
            if (drawerLayout.isDrawerOpen(navigationView)) {
                drawerLayout.closeDrawer(navigationView)
            } else {
                drawerLayout.openDrawer(navigationView)
            }
        }

        val emaill = intentData?.getString(Constants.INTENT_EMAIL)
        studentChallengeCollectionRef.whereEqualTo("email", emaill)
            .addSnapshotListener { snapshot, exception ->

                if (exception != null) {
                    // Handle errors
                    Log.e(ContentValues.TAG, "Error getting documents", exception)
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    // If there are documents, retrieve challenge titles and display them
                    val document = snapshot.documents[0]
                    val challengeTitles = document["challengeTitle"] as? List<String> ?: emptyList()

                    val titlesText = challengeTitles.joinToString("\n") // Join titles with newline
                    challengess.text = titlesText // Display titles in TextView
                }

                if(snapshot != null && !snapshot.isEmpty){
                    val document = snapshot.documents[0]
                    val flag = document["emptyChallenges"]
                    if(flag as Boolean){
                        challengess.text = "No challenges started"
                    }
                }



            }


        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.MealPlans -> {
                    // Handle meal item press
                    drawerLayout.close()
                    val intent = Intent(this, MealPlans::class.java)
                    startActivity(intent)
                    true
                }

                R.id.Workout -> {
                    // Handle workout item press
                    drawerLayout.close()
                    val intent = Intent(this, WorkoutPlan::class.java)
                    startActivity(intent)
                    true
                }

                R.id.Forums -> {
                    // Handle forum item press
                    drawerLayout.close()
                    val intent = Intent(this, support::class.java)
                    intentData.let {
                        val email = intentData?.getString(Constants.INTENT_EMAIL)
                        intent.putExtra(Constants.INTENT_EMAIL, email)
                    }
                    intent.putExtra(Constants.INTENT_NAME, nameItem.toString())
                    startActivity(intent)
                    true
                }

                R.id.Challenges -> {
                    // Handle challenges item press
                    drawerLayout.close()
                    val intent = Intent(this, challenges::class.java)
                    intentData.let {
                        val email = intentData?.getString(Constants.INTENT_EMAIL)
                        intent.putExtra(Constants.INTENT_EMAIL, email)
                    }
                    startActivity(intent)
                    true
                }

                R.id.WaterIntake -> {
                    // Handle water item press
                    drawerLayout.close()
                    val intent = Intent(this, waterIntake::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }






        intentData.let {
            val email = intentData?.getString(Constants.INTENT_EMAIL)
            if (email.toString().isNotEmpty()) {
                student_collection_ref.whereEqualTo("email", email.toString())
                    .addSnapshotListener { querySnapshot, exception ->
                        if (exception != null) {
                            // Handle errors
                            Log.e(ContentValues.TAG, "Error fetching user", exception)
                            return@addSnapshotListener
                        }

                        if (querySnapshot != null) {
                            for (document in querySnapshot) {
                                // Get the ID of the document
                                val documentId = document.id
                                val newStudentRef = db.collection("student").document(documentId)
                                newStudentRef.get()
                                    .addOnSuccessListener { document ->
                                        if (document != null) {
                                            // Access the email field
                                            val fname = document.getString("fname")
                                            val lname = document.getString("lname")
                                            if (fname != null) {
                                                if (nameItem != null) {
                                                    nameItem.title = "$fname $lname"
                                                }

                                            } else {
                                                Toast.makeText(
                                                    this@MainHome,
                                                    "Email field is null or doesn't exist",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        } else {
                                            Toast.makeText(
                                                this@MainHome,
                                                "User Email Doesn't exist",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                    .addOnFailureListener { exception ->
                                        Toast.makeText(
                                            this@MainHome,
                                            "Fetching failed: ${exception.message}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                            }
                        }
                    }
            }
        }


        // Set up ViewPager2 adapter
        adapter = RecipeAdapter(recipes, this, this)
        viewPager.adapter = adapter

        // Handle button clicks
        previousButton.setOnClickListener {
            viewPager.currentItem -= 1
        }

        nextButton.setOnClickListener {
            viewPager.currentItem += 1
        }

    }






}
