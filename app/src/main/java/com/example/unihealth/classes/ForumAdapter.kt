import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.unihealth.R
import com.example.unihealth.classes.Forum
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

class ForumAdapter(private val forumItems: List<Forum>, private val context: Context) :
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
        private val titleTextView: TextView = itemView.findViewById(R.id.titleForum)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionForum)
        private val timeTextView: TextView = itemView.findViewById(R.id.timeForum)
        private val replyButton: Button = itemView.findViewById(R.id.replyButton)
        private var popupWindow: PopupWindow? = null
        fun hideEmail(email: String): String {
            val atIndex = email.indexOf('@')
            val hiddenLength = atIndex - 1 // Length of characters to hide excluding '@'
            val hiddenPart = "*".repeat(hiddenLength)
            return "${email.substring(0, 1)}$hiddenPart${email.substring(atIndex)}"
        }

        fun bind(forumItem: Forum) {

            itemView.apply {

                usernameForum.text = "${forumItem.name} ~ ${hideEmail(forumItem.email)}"
                titleTextView.text = forumItem.title
                descriptionTextView.text = forumItem.description
                val timestampText = formatTimestamp(forumItem.timestamp)
                timeTextView.text = timestampText

                replyButton.setOnClickListener {
                    showPopup()
                }
            }
        }

        private fun showPopup() {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val popupView = inflater.inflate(R.layout.popup, null)
            val saveButton = popupView.findViewById<Button>(R.id.btnAddMessagePopUp)
            val addOrReply = popupView.findViewById<TextView>(R.id.addOrReply)
            val editTextInputLayoutMessage =
                popupView.findViewById<TextInputLayout>(R.id.forumMessage)
            val editTextInputLayoutDescription =
                popupView.findViewById<TextInputLayout>(R.id.forumDescription)
            addOrReply.text = "Reply"
            val popupWindow = PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
            )
            popupWindow.showAtLocation(popupView, Gravity.CENTER_HORIZONTAL, 0, -405)

            saveButton.setOnClickListener {
                // Handle saving data or any other action
                popupWindow.dismiss()
            }
        }


        private fun formatTimestamp(timestamp: Any): String {
            return when (timestamp) {
                is Timestamp -> {
                    val date = timestamp.toDate()
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    dateFormat.format(date)
                }

                else -> "Timestamp not available"
            }
        }
    }
}