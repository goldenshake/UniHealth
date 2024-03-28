package com.example.unihealth.classes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.unihealth.R

class WorkoutAdapter(private val workouts: MutableList<BodyWorkoutPlan>) :
    RecyclerView.Adapter<WorkoutViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_meal, parent, false)
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

}

class WorkoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageView: ImageView = itemView.findViewById(R.id.imageView)
    private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
    private val ingredientsTextView: TextView = itemView.findViewById(R.id.ingredientsTextView)
    private val calorieTextView: TextView = itemView.findViewById(R.id.calorieTextView)
    private val category_icon1: ImageView = itemView.findViewById(R.id.category_icon1)
    private val category_icon2: ImageView = itemView.findViewById(R.id.category_icon2)
    fun bind(workout: BodyWorkoutPlan) {
        imageView.setImageResource(workout.image)
//        nameTextView.text = workout.name
        ingredientsTextView.textSize = 20f
        ingredientsTextView.text = "${workout.exercises.joinToString(", ")}"
        calorieTextView.text = "Duration: ${workout.durationInMinutes} mins"


        when (workout.exercises[0]) {
            "Deadlifts", "Pull-Ups" -> category_icon1.setImageResource(R.drawable.back)
        }
    }
}