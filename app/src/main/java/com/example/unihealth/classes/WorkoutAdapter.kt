package com.example.unihealth.classes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unihealth.R

class WorkoutAdapter(
    private val workouts: MutableList<BodyWorkoutPlan>,
    private val fragmentManager: FragmentManager
) :
    RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_workout, parent, false)
        return WorkoutViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val workout = workouts[position]
        holder.bind(workout)
    }

    override fun getItemCount(): Int {
        return workouts.size
    }

    fun updateList(newList: List<BodyWorkoutPlan>) {
        workouts.clear()
        workouts.addAll(newList)
        notifyDataSetChanged()
    }

    inner class WorkoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val ingredientsTextView: TextView = itemView.findViewById(R.id.ingredientsTextView)
        private val calorieTextView: TextView = itemView.findViewById(R.id.calorieTextView)
        private val category_icon1: ImageView = itemView.findViewById(R.id.category_icon1)
        private val imageView4: ImageView = itemView.findViewById(R.id.imageView4)

        fun bind(workout: BodyWorkoutPlan) {
            var cat1 = 0
            imageView.setImageResource(workout.image)
            nameTextView.text = "${workout.exercises[0]}"
            if (workout.sets == "0") {
                ingredientsTextView.visibility = View.GONE
            } else {
                ingredientsTextView.visibility = View.VISIBLE
                ingredientsTextView.text = workout.sets
            }
            if (workout.durationInMinutes == 0) {
                imageView4.visibility = View.GONE
                calorieTextView.visibility = View.GONE
            } else {
                imageView4.visibility = View.VISIBLE
                calorieTextView.visibility = View.VISIBLE
                calorieTextView.text = "${workout.durationInMinutes} mins"
            }



            if (workout.tags[0] == "Muscle Gain") {
                if (workout.exercises.size == 2) {
                    category_icon1.visibility = View.VISIBLE
                    when (workout.exercises[1]) {
                        "Back" -> {
                            category_icon1.setImageResource(R.drawable.back)
                            cat1 = R.drawable.back
                        }
                        "Legs" -> {
                            category_icon1.setImageResource(R.drawable.leg)
                            cat1 = R.drawable.leg
                        }
                        "Chest" -> {
                            category_icon1.setImageResource(R.drawable.chest)
                            cat1 = R.drawable.chest
                        }
                        "Shoulders" -> {
                            category_icon1.setImageResource(R.drawable.shoulders)
                            cat1 = R.drawable.shoulders
                        }
                        "Bicep" -> {
                            category_icon1.setImageResource(R.drawable.muscle)
                            cat1 = R.drawable.muscle
                        }
                        "Tricep" -> {
                            category_icon1.setImageResource(R.drawable.triceps)
                            cat1 = R.drawable.triceps
                        }

                    }
                }
            } else {
                category_icon1.visibility = View.GONE
            }

            itemView.setOnClickListener {
                val modalBottomSheet = BottomSheetWorkoutPlan.newInstance(workout.exercises[0], workout.image, workout.durationInMinutes, workout.sets, cat1)
                modalBottomSheet.show(fragmentManager, BottomSheetWorkoutPlan.TAG)
//                showPopup(meal)
            }





        }
    }
}

