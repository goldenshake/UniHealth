import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.unihealth.R
import com.example.unihealth.RecipeMealPlans

class MealAdapter(private val meals: MutableList<RecipeMealPlans>) : RecyclerView.Adapter<MealViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_meal, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = meals[position]
        holder.bind(meal)
    }

    override fun getItemCount(): Int {
        return meals.size
    }

    fun updateList(newList: List<RecipeMealPlans>) {
        meals.clear()
        meals.addAll(newList)
        notifyDataSetChanged()
    }

}

class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageView: ImageView = itemView.findViewById(R.id.imageView)
    private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
    private val ingredientsTextView: TextView = itemView.findViewById(R.id.ingredientsTextView)
    private val calorieTextView: TextView = itemView.findViewById(R.id.calorieTextView)

    fun bind(meal: RecipeMealPlans) {
        imageView.setImageResource(meal.image)
        nameTextView.text = meal.name
        ingredientsTextView.text = "Ingredients: ${meal.ingredients.joinToString(", ")}"
        calorieTextView.text = "Calories: ${meal.calories}"
    }
}
