package com.example.unihealth.classes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.unihealth.R

class MealAdapter(private val meals: MutableList<RecipeMealPlans>) :
    RecyclerView.Adapter<MealViewHolder>() {
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
    private val category_icon1: ImageView = itemView.findViewById(R.id.category_icon1)
    private val category_icon2: ImageView = itemView.findViewById(R.id.category_icon2)
    private val meal_card: CardView = itemView.findViewById(R.id.meal_card)

    fun bind(meal: RecipeMealPlans) {
        val category = meal.categories
        imageView.setImageResource(meal.image)
        nameTextView.text = meal.name
        ingredientsTextView.text = "${meal.ingredients.joinToString(", ")}"
        calorieTextView.text = "Calories ~ ${meal.calories}"


        when (category.getOrNull(0)) {
            "Vegetarian" -> category_icon1.setImageResource(R.drawable.vegetarian)
            "Dairy Free" -> category_icon1.setImageResource(R.drawable.dairyfree)
            "Low Carbs" -> category_icon1.setImageResource(R.drawable.lowcarb)
            "Nut Free" -> category_icon1.setImageResource(R.drawable.nutfree)
            "Vegan" -> category_icon1.setImageResource(R.drawable.vegan)
            else -> category_icon1.visibility = View.GONE
        }
        if (category.size == 2) {
            when (category[1]) {
                "Vegetarian" -> category_icon2.setImageResource(R.drawable.vegetarian)
                "Dairy Free" -> category_icon2.setImageResource(R.drawable.dairyfree)
                "Low Carbs" -> category_icon2.setImageResource(R.drawable.lowcarb)
                "Nut Free" -> category_icon2.setImageResource(R.drawable.nutfree)
                "Vegan" -> category_icon2.setImageResource(R.drawable.vegan)
                else -> category_icon1.visibility = View.GONE
            }
        } else {
            category_icon2.visibility = View.GONE
        }

    }
}
