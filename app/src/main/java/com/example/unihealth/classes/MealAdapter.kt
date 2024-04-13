package com.example.unihealth.classes

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unihealth.R
import java.util.Locale

class MealAdapter(
    private val meals: MutableList<RecipeMealPlansDB>,
    private val activity: AppCompatActivity,
    private val context: Context,
    private val fragmentManager: FragmentManager
) :
    RecyclerView.Adapter<MealAdapter.MealViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_meal, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = meals[position]
        holder.bind(meal)
    }

    override fun getItemCount(): Int {
        return meals.size
    }

    fun updateList(newList: List<RecipeMealPlansDB>) {
        meals.clear()
        meals.addAll(newList)
        notifyDataSetChanged()
    }


    inner class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val ingredientsTextView: TextView = itemView.findViewById(R.id.ingredientsTextView)
        private val calorieTextView: TextView = itemView.findViewById(R.id.calorieTextView)
        private val category_icon1: ImageView = itemView.findViewById(R.id.category_icon1)
        private val category_icon2: ImageView = itemView.findViewById(R.id.category_icon2)
        private val imageView4: ImageView = itemView.findViewById(R.id.imageView4)
        private val meal_card: CardView = itemView.findViewById(R.id.meal_card)


        @SuppressLint("DiscouragedApi")
        fun bind(meal: RecipeMealPlansDB) {
            val category = meal.categories
//        imageView.setImageResource(meal.image)
            nameTextView.text = meal.name
            imageView4.setImageResource(R.drawable.calories)
            ingredientsTextView.text = "${meal.ingredients.joinToString(", ")}"
            calorieTextView.text = "${meal.calories}"


            val drawableResId = context.resources.getIdentifier(
                meal.name.lowercase(Locale.ROOT).replace(" ", "_"), "drawable", context.packageName)
            imageView.setImageResource(drawableResId)

            var cat1 = 0
            var cat2 = 0

            when (category.size) {
                1 -> {
                    when (category.getOrNull(0)) {
                        "Vegetarian" -> {
                            category_icon1.setImageResource(R.drawable.vegetarian)
                            cat1 = R.drawable.vegetarian
                        }
                        "Dairy Free" -> {
                            category_icon1.setImageResource(R.drawable.dairyfree)
                            cat1 = R.drawable.dairyfree
                        }
                        "Low Carbs" -> {
                            category_icon1.setImageResource(R.drawable.lowcarb)
                            cat1 = R.drawable.lowcarb
                        }
                        "Nut Free" -> {
                            category_icon1.setImageResource(R.drawable.nutfree)
                            cat1 = R.drawable.nutfree
                        }
                        "Vegan" -> {
                            category_icon1.setImageResource(R.drawable.vegan)
                            cat1 = R.drawable.vegan
                        }

                    }
                    category_icon2.visibility = View.GONE
                }

                2 -> {

                    when (category.getOrNull(0)) {
                        "Vegetarian" -> {
                            category_icon1.setImageResource(R.drawable.vegetarian)
                            cat1 = R.drawable.vegetarian
                        }
                        "Dairy Free" -> {
                            category_icon1.setImageResource(R.drawable.dairyfree)
                            cat1 = R.drawable.dairyfree
                        }
                        "Low Carbs" -> {
                            category_icon1.setImageResource(R.drawable.lowcarb)
                            cat1 = R.drawable.lowcarb
                        }
                        "Nut Free" -> {
                            category_icon1.setImageResource(R.drawable.nutfree)
                            cat1 = R.drawable.nutfree
                        }
                        "Vegan" -> {
                            category_icon1.setImageResource(R.drawable.vegan)
                            cat1 = R.drawable.vegan
                        }
                    }
                    category_icon2.visibility = View.VISIBLE
                    when (category.getOrNull(1)) {
                        "Vegetarian" -> {
                            category_icon2.setImageResource(R.drawable.vegetarian)
                            cat2 = R.drawable.vegetarian
                        }
                        "Dairy Free" -> {
                            category_icon2.setImageResource(R.drawable.dairyfree)
                            cat2 = R.drawable.dairyfree
                        }
                        "Low Carbs" -> {
                            category_icon2.setImageResource(R.drawable.lowcarb)
                            cat2 = R.drawable.lowcarb
                        }
                        "Nut Free" -> {
                            category_icon2.setImageResource(R.drawable.nutfree)
                            cat2 = R.drawable.nutfree
                        }
                        "Vegan" -> {
                            category_icon2.setImageResource(R.drawable.vegan)
                            cat2 = R.drawable.vegan
                        }
                    }
                }

                else -> {
                    category_icon1.visibility = View.GONE
                    category_icon2.visibility = View.GONE
                }
            }
            itemView.setOnClickListener {
                val modalBottomSheet = MealExpandBottomSheet.newInstance(meal.name,meal.ingredientsMax.joinToString("\n"), meal.calories.toString(), cat1, cat2)
                modalBottomSheet.show(fragmentManager, MealExpandBottomSheet.TAG)
//                showPopup(meal)
            }
        }


        private fun showPopup(meal: RecipeMealPlansDB) {

            if (!activity.isFinishing && !activity.isDestroyed) {

                val category = meal.categories
                val inflater =
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val popupView = inflater.inflate(R.layout.expandedmealpopup, null)
                val mealPopUpCloseButton =
                    popupView.findViewById<ImageView>(R.id.mealPopUpCloseButton)
                val mealPopUpImage = popupView.findViewById<ImageView>(R.id.mealPopUpImage)
                val mealPopUpNameTextView =
                    popupView.findViewById<TextView>(R.id.mealPopUpNameTextView)
                val mealPopUpIngredientsTextView =
                    popupView.findViewById<TextView>(R.id.mealPopUpIngredientsTextView)
                val mealPopUpCalorieTextView =
                    popupView.findViewById<TextView>(R.id.mealPopUpCalorieTextView)
                val mealPopUpCategory_icon1 =
                    popupView.findViewById<ImageView>(R.id.mealPopUpCategory_icon1)
                val mealPopUpCategory_icon2 =
                    popupView.findViewById<ImageView>(R.id.mealPopUpCategory_icon2)
                val backgroundDimmer = popupView.findViewById<View>(R.id.backgroundDimmer)
                backgroundDimmer.visibility = View.VISIBLE


                mealPopUpNameTextView.text = meal.name
                mealPopUpIngredientsTextView.text = "${meal.ingredientsMax.joinToString(", \n")}"
                mealPopUpCalorieTextView.text = meal.calories.toString()
                when (category.size) {
                    1 -> {
                        when (category.getOrNull(0)) {
                            "Vegetarian" -> mealPopUpCategory_icon1.setImageResource(R.drawable.vegetarian)
                            "Dairy Free" -> mealPopUpCategory_icon1.setImageResource(R.drawable.dairyfree)
                            "Low Carbs" -> mealPopUpCategory_icon1.setImageResource(R.drawable.lowcarb)
                            "Nut Free" -> mealPopUpCategory_icon1.setImageResource(R.drawable.nutfree)
                            "Vegan" -> mealPopUpCategory_icon1.setImageResource(R.drawable.vegan)

                        }
                        mealPopUpCategory_icon2.visibility = View.GONE
                    }

                    2 -> {

                        when (category.getOrNull(0)) {
                            "Vegetarian" -> mealPopUpCategory_icon1.setImageResource(R.drawable.vegetarian)
                            "Dairy Free" -> mealPopUpCategory_icon1.setImageResource(R.drawable.dairyfree)
                            "Low Carbs" -> mealPopUpCategory_icon1.setImageResource(R.drawable.lowcarb)
                            "Nut Free" -> mealPopUpCategory_icon1.setImageResource(R.drawable.nutfree)
                            "Vegan" -> mealPopUpCategory_icon1.setImageResource(R.drawable.vegan)
                        }
                        mealPopUpCategory_icon2.visibility = View.VISIBLE
                        when (category.getOrNull(1)) {
                            "Vegetarian" -> mealPopUpCategory_icon2.setImageResource(R.drawable.vegetarian)
                            "Dairy Free" -> mealPopUpCategory_icon2.setImageResource(R.drawable.dairyfree)
                            "Low Carbs" -> mealPopUpCategory_icon2.setImageResource(R.drawable.lowcarb)
                            "Nut Free" -> mealPopUpCategory_icon2.setImageResource(R.drawable.nutfree)
                            "Vegan" -> mealPopUpCategory_icon2.setImageResource(R.drawable.vegan)
                        }
                    }

                    else -> {
                        mealPopUpCategory_icon1.visibility = View.GONE
                        mealPopUpCategory_icon2.visibility = View.GONE
                    }
                }
                val popupWindow = PopupWindow(
                    popupView,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    true
                )

                // Set the focusable property of the popup window
                popupWindow.isFocusable = true


                mealPopUpCloseButton.setOnClickListener {
                    popupWindow.dismiss()
                }
                popupWindow.showAtLocation(popupView, Gravity.CENTER_HORIZONTAL, 0, -405)

            }
        }

    }

}


