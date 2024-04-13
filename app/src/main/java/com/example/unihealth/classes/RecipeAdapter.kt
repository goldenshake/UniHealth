package com.example.unihealth.classes

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.unihealth.MainHome
import com.example.unihealth.MealPlans
import com.example.unihealth.R
import com.google.android.material.card.MaterialCardView

class RecipeAdapter(
    private val recipes: List<Recipe>,
    private val context: Context,
    private val activity: AppCompatActivity
) :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipeName: TextView = itemView.findViewById(R.id.recipeName)
        val recipeImage: ImageView = itemView.findViewById(R.id.recipeImage)
        val recipeCard: MaterialCardView = itemView.findViewById(R.id.recipeItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.recipeName.text = recipe.name
        holder.recipeImage.setImageResource(recipe.imageResource)
        holder.recipeCard.setOnClickListener {
            val intent = Intent(activity, MealPlans::class.java)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return recipes.size
    }
}


