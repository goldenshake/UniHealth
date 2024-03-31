package com.example.unihealth

import ForumAdapter
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unihealth.classes.Constants
import com.example.unihealth.classes.Forum
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class support : AppCompatActivity() {
    private var popupWindow: PopupWindow? = null
    private lateinit var showPopupButton: Button
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val forum_collection_ref: CollectionReference = db.collection("forum")
    private lateinit var forumAdapter: ForumAdapter
    private var email: String? = null
    private var name: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_support)

        val forumItemsList = mutableListOf<Forum>()
        email = intent.getStringExtra(Constants.INTENT_EMAIL)
        name = intent.getStringExtra(Constants.INTENT_NAME)
        forumAdapter = ForumAdapter(forumItemsList, this, email.toString(), name.toString())
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewMessages)
        recyclerView.adapter = forumAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        forum_collection_ref.orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    // Handle error
                    return@addSnapshotListener
                }

                // Clear existing data in the list
                forumItemsList.clear()

                // Iterate through documents in the snapshot
                if (snapshot != null) {
                    for (document in snapshot.documents) {
                        val forumItem = document.toObject(Forum::class.java)
                        forumItemsList.add(forumItem!!)
                    }
                }

                // Notify adapter of data change
                forumAdapter.notifyDataSetChanged()
            }
//        forum_collection_ref.get()
//            .addOnSuccessListener { documents ->
//                for (document in documents) {
//                    val forumItem = document.toObject(Forum::class.java)
//                    forumItemsList.add(forumItem)
//                }
//                forumAdapter.notifyDataSetChanged()
//                Toast.makeText(
//                    this@support,
//                    "Messages fetched",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//            .addOnFailureListener { exception ->
//                Toast.makeText(
//                    this@support,
//                    "Error getting messages: {$exception}",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }



        showPopupButton = findViewById(R.id.showPopUpButton)
        showPopupButton.setOnClickListener {
            showPopup()
        }

    }

    private fun showPopup() {
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.popup, null)
        val saveButton = popupView.findViewById<Button>(R.id.btnAddMessagePopUp)
        val addOrReply = popupView.findViewById<TextView>(R.id.addOrReply)
        val editTextInputLayoutMessage = popupView.findViewById<TextInputLayout>(R.id.forumMessage)
        val editTextInputLayoutDescription =
            popupView.findViewById<TextInputLayout>(R.id.forumDescription)
        val width = LinearLayout.LayoutParams.MATCH_PARENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        popupWindow = PopupWindow(popupView, width, height, true)

        addOrReply.text = "Add Message"
        popupWindow?.showAtLocation(
            popupView,
            Gravity.CENTER_HORIZONTAL,
            0,
            -405
        )
        val messageEditText = editTextInputLayoutMessage?.editText
        val descriptionEditText = editTextInputLayoutDescription?.editText

        messageEditText?.doOnTextChanged { text, _, _, _ ->
            // You can handle text changes here
        }

        descriptionEditText?.doOnTextChanged { text, _, _, _ ->
            // You can handle text changes here
        }
        popupWindow?.showAsDropDown(showPopupButton, 0, 0)


        saveButton.setOnClickListener {

            val forum = Forum(
                "",
                name.toString(),
                email.toString(),
                messageEditText?.text.toString(),
                descriptionEditText?.text.toString(),
                FieldValue.serverTimestamp(),
                emptyList()
            )
            forum_collection_ref.add(forum)
                .addOnSuccessListener { documentReference ->
                    // Retrieve the generated ID
                    val forumId = documentReference.id

                    // Update the document with the ID
                    forum_collection_ref.document(documentReference.id)
                        .update("id", forumId)
                        .addOnSuccessListener {
                            Toast.makeText(
                                this@support,
                                "Message Saved",
                                Toast.LENGTH_SHORT
                            ).show()
                            popupWindow?.dismiss()
                        }
                        .addOnFailureListener { exception ->
                            Toast.makeText(
                                this@support,
                                "Failed to Update ID: ${exception.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(
                        this@support,
                        "Failed to Save: ${exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        popupWindow?.dismiss()
    }
}