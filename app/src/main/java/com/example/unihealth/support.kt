package com.example.unihealth

import ForumAdapter
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
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
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class support : AppCompatActivity() {
    private var popupWindow: PopupWindow? = null
    private lateinit var showPopupButton: FloatingActionButton
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
        forumAdapter = ForumAdapter(forumItemsList, this, email.toString(), name.toString(), this)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewMessages)
        recyclerView.adapter = forumAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        val topAppBar: MaterialToolbar = findViewById(R.id.topAppBar)
        val menu = topAppBar.menu
        val nameItem = menu.findItem(R.id.name)
        nameItem.title = "Forum"

        topAppBar.setNavigationOnClickListener {
            // Handle navigation icon press
            finish()
        }

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

        showPopupButton = findViewById(R.id.showPopUpButton)
        showPopupButton.setOnClickListener {
            showPopup()
        }

    }

    private fun showPopup() {
        if (!this@support.isFinishing && !this@support.isDestroyed) {
            val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val popupView = inflater.inflate(R.layout.popup, null)
            val saveButton = popupView.findViewById<Button>(R.id.btnAddMessagePopUp)
            val addOrReply = popupView.findViewById<TextView>(R.id.addOrReply)
            val emptyAddDescError = popupView.findViewById<TextView>(R.id.emptyAddDescError)
            val emptyAddTitleError = popupView.findViewById<TextView>(R.id.emptyAddTitleError)
            val editTextInputLayoutMessage = popupView.findViewById<TextInputLayout>(R.id.forumMessage)
            val editTextInputLayoutDescription =
                popupView.findViewById<TextInputLayout>(R.id.forumDescription)
            val width = LinearLayout.LayoutParams.MATCH_PARENT
            val height = LinearLayout.LayoutParams.WRAP_CONTENT
            val popUpCloseButton =
                popupView.findViewById<Button>(R.id.popUpCloseButton)
            popupWindow = PopupWindow(popupView, width, height, true)
            val backgroundDimmer = popupView.findViewById<View>(R.id.backgroundDimmer)
            backgroundDimmer.visibility = View.VISIBLE
            addOrReply.text = "Add Post"
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

            emptyAddTitleError.visibility = View.GONE
            emptyAddDescError.visibility = View.GONE
            saveButton.setOnClickListener {
                if(messageEditText?.text.toString().isEmpty() && descriptionEditText?.text.toString().isEmpty()){
                    emptyAddTitleError.visibility = View.VISIBLE
                    emptyAddTitleError.text = "Please Enter Title"
                    emptyAddDescError.visibility = View.VISIBLE
                    emptyAddDescError.text = "Please Enter Description"
                }else if(descriptionEditText?.text.toString().isEmpty()){
                    emptyAddTitleError.visibility = View.GONE
                    emptyAddDescError.visibility = View.VISIBLE
                    emptyAddDescError.text = "Please Enter Description"
                }else if(messageEditText?.text.toString().isEmpty()){
                    emptyAddDescError.visibility = View.GONE
                    emptyAddTitleError.visibility = View.VISIBLE
                    emptyAddTitleError.text = "Please Enter Description"
                }
                else{
                    val forum = Forum(
                        "",
                        name.toString(),
                        email.toString(),
                        messageEditText?.text.toString(),
                        descriptionEditText?.text.toString(),
                        FieldValue.serverTimestamp(),
                        emptyList(),
                        false
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
            popUpCloseButton.setOnClickListener {
                popupWindow?.dismiss()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        popupWindow?.dismiss()
    }
}