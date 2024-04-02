import android.content.Context
import android.graphics.Typeface
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionForum)
        private val timeTextView: TextView = itemView.findViewById(R.id.timeForum)
        private val replyButton: Button = itemView.findViewById(R.id.replyButton)
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
                titleTextView.text = forumItem.title
                descriptionTextView.text = forumItem.description
                val timestampText = formatTimestamp(forumItem.timestamp)
                timeTextView.text = timestampText
                youForum.visibility = View.GONE
                if (forumItem.email == currentUserEmail) {
                    youForum.text = "YOU"
                    youForum.visibility = View.VISIBLE
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

            if (!activity.isFinishing && !activity.isDestroyed) {}

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
    }
}