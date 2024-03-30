import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.unihealth.R
import com.example.unihealth.classes.Forum

class ForumAdapter(private val forumItems: List<Forum>) :
    RecyclerView.Adapter<ForumAdapter.ForumViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForumViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_forum, parent, false)
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
            }
        }
    }
}