package com.example.unihealth.classes

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.unihealth.R
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class ChallengesAdapter(private val challenges: MutableList<challengePlan>, private val currentEmail: String) :
    RecyclerView.Adapter<ChallengeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.challenge_item, parent, false)
        return ChallengeViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
        val challenge = challenges[position]
        holder.bind(challenge, currentEmail)
    }

    override fun getItemCount(): Int {
        return challenges.size
    }
}

class ChallengeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val studentChallengeCollectionRef: CollectionReference = db.collection("challenge")
    private val titleView: TextView = itemView.findViewById(R.id.challengeTitle)
    private val descriptionView: TextView = itemView.findViewById(R.id.descriptionChallenge)
    private val difficultyTextView: TextView = itemView.findViewById(R.id.difficultyTextView)
    private val challengeStartButton: Button = itemView.findViewById(R.id.challengeStartButton)
    private val challengeCancelButton: Button = itemView.findViewById(R.id.challengeCancelButton)

    fun bind(challenge: challengePlan, currentEmail: String) {
        titleView.text = challenge.title
        descriptionView.text = challenge.description
        difficultyTextView.text = challenge.difficulty
        challengeCancelButton.visibility = View.GONE
        // Check if the user has already started this challenge
        studentChallengeCollectionRef.whereEqualTo("email", currentEmail)
            .whereArrayContains("challengeTitle", challenge.title)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    // If the user has started the challenge, show the cancel button
                    challengeCancelButton.visibility = View.VISIBLE
                    challengeStartButton.visibility = View.GONE
                } else {
                    // If not, hide the cancel button
                    challengeCancelButton.visibility = View.GONE
                    challengeStartButton.visibility = View.VISIBLE
                }
            }
            .addOnFailureListener { e ->
                // Handle failure
                Log.e(TAG, "Error checking if challenge started", e)
            }

        challengeStartButton.setOnClickListener {
            // Check if a document with the user's email already exists
            studentChallengeCollectionRef.whereEqualTo("email", currentEmail)
                .get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        // If a document exists, update it with the new challenge title
                        val document = documents.documents[0]
                        val challengeTitles = document["challengeTitle"] as? MutableList<String> ?: mutableListOf()
                        challengeTitles.add(challenge.title) // Add the new challenge title
                        document.reference.update("challengeTitle", challengeTitles)
                            .addOnSuccessListener {
                                // Handle success
                                Log.d(TAG, "Challenge title added successfully")
                                challengeCancelButton.visibility = View.VISIBLE
                                challengeStartButton.visibility = View.GONE
                            }
                            .addOnFailureListener { e ->
                                // Handle failure
                                Log.e(TAG, "Error adding challenge title", e)
                            }
                    } else {
                        // If no document exists, create a new one
                        val studentChallenge = ChallengesDB(mutableListOf(challenge.title), currentEmail)
                        studentChallengeCollectionRef.add(studentChallenge)
                            .addOnSuccessListener { documentReference ->
                                // Handle success
                                Log.d(TAG, "Challenge document added successfully")
                                challengeCancelButton.visibility = View.VISIBLE
                                challengeStartButton.visibility = View.GONE
                            }
                            .addOnFailureListener { e ->
                                // Handle failure
                                Log.e(TAG, "Error adding challenge document", e)
                            }
                    }
                }
                .addOnFailureListener { e ->
                    // Handle failure
                    Log.e(TAG, "Error getting documents", e)
                }
        }

        challengeCancelButton.setOnClickListener {
            // Remove the challenge title from the database
            studentChallengeCollectionRef.whereEqualTo("email", currentEmail)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val challengeTitles = document["challengeTitle"] as? MutableList<String>
                        challengeTitles?.remove(challenge.title)
                        document.reference.update("challengeTitle", challengeTitles)
                            .addOnSuccessListener {
                                // Handle success
                                Log.d(TAG, "Challenge title removed successfully")
                                challengeCancelButton.visibility = View.GONE
                                challengeStartButton.visibility = View.VISIBLE
                            }
                            .addOnFailureListener { e ->
                                // Handle failure
                                Log.e(TAG, "Error removing challenge title", e)
                            }
                    }
                }
                .addOnFailureListener { e ->
                    // Handle failure
                    Log.e(TAG, "Error getting documents", e)
                }
        }
    }
}
