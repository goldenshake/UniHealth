import android.content.ContentValues
import android.content.Context
import android.graphics.Typeface
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.unihealth.R
import com.example.unihealth.classes.Forum
import com.example.unihealth.classes.Reply
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Locale

class ForumAdapter(
    private val forumItems: List<Forum>,
    private val context: Context,
    private val currentUserEmail: String,
    private val currentUserName: String,
    private val activity: AppCompatActivity
) :
    RecyclerView.Adapter<ForumAdapter.ForumViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForumViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_forum, parent, false)
        return ForumViewHolder(view)

    }

    override fun onBindViewHolder(holder: ForumViewHolder, position: Int) {
        val currentItem = forumItems[position]
        holder.bind(currentItem)
    }

    override fun getItemCount() = forumItems.size

    inner class ForumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val usernameForum: TextView = itemView.findViewById(R.id.usernameForum)
        private val youForum: TextView = itemView.findViewById(R.id.youForum)
        private val titleTextView: TextView = itemView.findViewById(R.id.titleForum)
        private val editedText: TextView = itemView.findViewById(R.id.editedText)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionForum)
        private val timeTextView: TextView = itemView.findViewById(R.id.timeForum)
        private val replyButton: Button = itemView.findViewById(R.id.replyButton)
        private val deletebuttonForum: ImageView = itemView.findViewById(R.id.deletebuttonForum)
        private val editbuttonForum: ImageView = itemView.findViewById(R.id.editbuttonForum)
        private val replierTime: TextView = itemView.findViewById(R.id.textView5)
        private val replierDescription: TextView = itemView.findViewById(R.id.textView6)
        private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        private val replyContainer: LinearLayout = itemView.findViewById(R.id.replyContainer)
        private val forum_collection_ref: CollectionReference = db.collection("forum")

        private var popupWindow: PopupWindow? = null
        fun hideEmail(email: String): String {
            val atIndex = email.indexOf('@')
            val hiddenLength = atIndex - 1 // Length of characters to hide excluding '@'
            val hiddenPart = "*".repeat(hiddenLength)
            return "${email.substring(0, 2)}${
                hiddenPart.substring(
                    0,
                    1
                )
            }${email.substring(atIndex)}"
        }

        fun bind(forumItem: Forum) {

            itemView.apply {

                usernameForum.text = "${forumItem.name} ~ ${hideEmail(forumItem.email)}"
                titleTextView.text = forumItem.title + " "
                descriptionTextView.text = forumItem.description
                val timestampText = formatTimestamp(forumItem.timestamp)
                timeTextView.text = timestampText
                youForum.visibility = View.GONE
                deletebuttonForum.visibility = View.GONE
                editbuttonForum.visibility = View.GONE
                editedText.visibility = View.GONE


                if (forumItem.edited) {
                    editedText.visibility = View.VISIBLE
                    editedText.text = " (edited)"
                } else {
                    editedText.visibility = View.GONE
                }



                if (forumItem.email == currentUserEmail) {
                    youForum.text = "YOU"
                    youForum.visibility = View.VISIBLE
                    editbuttonForum.visibility = View.VISIBLE
                    deletebuttonForum.visibility = View.VISIBLE

                    deletebuttonForum.setOnClickListener {
                        forum_collection_ref.whereEqualTo("email", currentUserEmail)
                            .get()
                            .addOnSuccessListener { querySnapshot ->
                                for (document in querySnapshot) {
                                    // Get the ID of the document
                                    val documentId = document.id
                                    delete(documentId)
                                }
                            }
                            .addOnFailureListener { e ->
                                Log.e(ContentValues.TAG, "Error deleting post ", e)
                            }
                    }

                    editbuttonForum.setOnClickListener {
                        forum_collection_ref.whereEqualTo("email", currentUserEmail)
                            .get()
                            .addOnSuccessListener { querySnapshot ->
                                for (document in querySnapshot) {
                                    // Get the ID of the document
                                    val documentId = document.id
                                    update(
                                        documentId,
                                        titleTextView.text.toString(),
                                        descriptionTextView.text.toString()
                                    )
                                }
                            }
                            .addOnFailureListener { e ->
                                Log.e(ContentValues.TAG, "Error deleting post ", e)
                            }
                    }


                }



                replyContainer.removeAllViews()
                if (forumItem.replies.isEmpty()) {
                    replyContainer.visibility = View.GONE
                } else {
                    replyContainer.visibility = View.VISIBLE
                    for (reply in forumItem.replies) {
                        val replyLayout = LinearLayout(context).apply {
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            )
                            orientation = LinearLayout.VERTICAL
                        }

                        // TextView for name, email, and time
                        val nameEmailTimeTextView = TextView(context).apply {
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            )
                            text = "${reply.name} ~ ${hideEmail(reply.email)}\t\t\t\t${
                                formatReplyTimestamp(reply.timestamp)
                            }"
                            textSize = 12.0F
                            setTypeface(null, Typeface.BOLD_ITALIC)
                            setPadding(paddingLeft, paddingTop, paddingRight, 10)
                        }
                        replyLayout.addView(nameEmailTimeTextView)

                        // TextView for description
                        val replierDescriptionTextView = TextView(context).apply {
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            )
                            text = reply.message
                            setPadding(15, 30, paddingRight, 80)
                        }
                        replyLayout.addView(replierDescriptionTextView)

                        replyContainer.addView(replyLayout)
                    }
                }


                replyButton.setOnClickListener {
                    showPopup(replyButton)
                }
            }
        }


        private fun showPopup(anchorView: View) {

            if (!activity.isFinishing && !activity.isDestroyed) {

                val inflater =
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val popupView = inflater.inflate(R.layout.replypopup, null)
                val saveButton = popupView.findViewById<Button>(R.id.btnAddMessagePopUp)
                val addOrReplytitle = popupView.findViewById<TextView>(R.id.addOrReplytitle)
                val emptyReplyDescError = popupView.findViewById<TextView>(R.id.emptyReplyDescError)
                val editTextInputLayoutDescription =
                    popupView.findViewById<TextInputLayout>(R.id.forumDescription)

                val forumId = forumItems[adapterPosition].id
                Log.d("ForumAdapter", "Forum ID: $forumId")

                addOrReplytitle.text = titleTextView.text
                val popupWindow = PopupWindow(
                    popupView,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    true
                )
                popupWindow.showAtLocation(popupView, Gravity.CENTER_HORIZONTAL, 0, -405)
                saveButton.text = "Reply"
                saveButton.setOnClickListener {
                    // Handle saving data or any other action
                    val replyDescription = editTextInputLayoutDescription?.editText?.text.toString()
                    emptyReplyDescError.visibility = View.GONE
                    if (replyDescription.isEmpty()) {
                        emptyReplyDescError.visibility = View.VISIBLE
                        emptyReplyDescError.text = "Please Enter Description"
                    } else {
                        val replyData = Reply(
                            currentUserName,
                            currentUserEmail,
                            replyDescription,
                            Timestamp.now()
                        )
                        val parentMessageRef = forum_collection_ref.document(forumId)

                        parentMessageRef.collection("replies")
                            .add(replyData)
                            .addOnSuccessListener { documentReference ->
                                parentMessageRef.update("replies", FieldValue.arrayUnion(replyData))
                                popupWindow.dismiss()
                                Toast.makeText(context, "Reply saved", Toast.LENGTH_SHORT).show()

                            }
                            .addOnFailureListener { exception ->
                                Log.e("ForumAdapter", "Failed to save reply", exception)
                                Toast.makeText(context, "Failed to save reply", Toast.LENGTH_SHORT)
                                    .show()
                            }
                    }
                }

                popupWindow.showAsDropDown(anchorView)
            }
        }


        private fun formatTimestamp(timestamp: Any): String {
            return when (timestamp) {
                is Timestamp -> {
                    val date = timestamp.toDate()
                    val dateFormat =
                        SimpleDateFormat("MMMM dd, yyyy '@' HH:mm", Locale.getDefault())
                    dateFormat.format(date)
                }

                else -> "Timestamp not available"
            }
        }

        private fun formatReplyTimestamp(timestamp: Any): String {
            return when (timestamp) {
                is Timestamp -> {
                    val date = timestamp.toDate()
                    val dateFormat =
                        SimpleDateFormat("MMMM dd, yyyy '@' HH:mm", Locale.getDefault())
                    dateFormat.format(date)
                }

                else -> "Timestamp not available"
            }
        }


        private fun delete(id: String) {

            val inflater =
                context.getSystemService(AppCompatActivity.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val popupView = inflater.inflate(R.layout.deletepopup, null)
            val deleteButton = popupView.findViewById<Button>(R.id.btnDelMessagePopUp)

            val width = LinearLayout.LayoutParams.MATCH_PARENT
            val height = LinearLayout.LayoutParams.WRAP_CONTENT
            popupWindow = PopupWindow(popupView, width, height, true)
            popupWindow?.showAtLocation(
                popupView,
                Gravity.CENTER_HORIZONTAL,
                0,
                -405
            )
            popupWindow?.showAsDropDown(deletebuttonForum, 0, 0)

            deleteButton.setOnClickListener {
                val newBookRef = db.collection("forum").document(id)
                newBookRef.delete().addOnSuccessListener {
                    Toast.makeText(activity, "Post Successfully deleted", Toast.LENGTH_SHORT)
                        .show()
                    popupWindow?.dismiss()
                }.addOnFailureListener { e ->
                    Log.w("Firestore", "Error deleting document", e)
                    Toast.makeText(activity, "Error deleting Post", Toast.LENGTH_SHORT)
                        .show()
                }
            }


        }


        private fun update(id: String, title: String, desc: String) {

//            This is the popup to edit a field
            val inflater =
                context.getSystemService(AppCompatActivity.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val popupView = inflater.inflate(R.layout.editpopup, null)
            val saveButton = popupView.findViewById<Button>(R.id.btnAddMessagePopUp)
            val addOrReply = popupView.findViewById<TextView>(R.id.addOrReply)
            val emptyAddDescError = popupView.findViewById<TextView>(R.id.emptyAddDescError)
            val emptyAddTitleError = popupView.findViewById<TextView>(R.id.emptyAddTitleError)
            val editTextInputLayoutMessage =
                popupView.findViewById<TextInputLayout>(R.id.forumMessage)
            val editTextInputLayoutDescription =
                popupView.findViewById<TextInputLayout>(R.id.forumDescription)
            val width = LinearLayout.LayoutParams.MATCH_PARENT
            val height = LinearLayout.LayoutParams.WRAP_CONTENT
            popupWindow = PopupWindow(popupView, width, height, true)

            addOrReply.text = "Edit Post"
            popupWindow?.showAtLocation(
                popupView,
                Gravity.CENTER_HORIZONTAL,
                0,
                -405
            )
            val messageEditText = editTextInputLayoutMessage?.editText
            val descriptionEditText = editTextInputLayoutDescription?.editText
            messageEditText?.setText(title)
            descriptionEditText?.setText(desc)
            messageEditText?.doOnTextChanged { text, _, _, _ ->
                // handle text changes here
            }

            descriptionEditText?.doOnTextChanged { text, _, _, _ ->
                // handle text changes here
            }
            popupWindow?.showAsDropDown(editbuttonForum, 0, 0)

            emptyAddTitleError.visibility = View.GONE
            emptyAddDescError.visibility = View.GONE
            saveButton.setOnClickListener {
                if (messageEditText?.text.toString()
                        .isEmpty() && descriptionEditText?.text.toString().isEmpty()
                ) {
                    emptyAddTitleError.visibility = View.VISIBLE
                    emptyAddTitleError.text = "Please Enter Title"
                    emptyAddDescError.visibility = View.VISIBLE
                    emptyAddDescError.text = "Please Enter Description"
                } else if (descriptionEditText?.text.toString().isEmpty()) {
                    emptyAddTitleError.visibility = View.GONE
                    emptyAddDescError.visibility = View.VISIBLE
                    emptyAddDescError.text = "Please Enter Description"
                } else if (messageEditText?.text.toString().isEmpty()) {
                    emptyAddDescError.visibility = View.GONE
                    emptyAddTitleError.visibility = View.VISIBLE
                    emptyAddTitleError.text = "Please Enter Description"
                } else {

                    forum_collection_ref.document(id)
                        .update(
                            mapOf(
                                "title" to messageEditText?.text.toString(),
                                "description" to descriptionEditText?.text.toString(),
                                "edited" to true
                            )
                        )
                        .addOnSuccessListener {
                            Toast.makeText(
                                activity,
                                "Message Edited",
                                Toast.LENGTH_SHORT
                            ).show()

                            popupWindow?.dismiss()
                        }
                        .addOnFailureListener { exception ->
                            Toast.makeText(
                                activity,
                                "Failed to Update ID: ${exception.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                }
            }


        }
    }
}