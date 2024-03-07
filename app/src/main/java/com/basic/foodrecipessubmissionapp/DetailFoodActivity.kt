package com.basic.foodrecipessubmissionapp

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide

class DetailFoodActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        val FOOD_RECIPE = "food_recipe"
    }

    private lateinit var sourceUrl: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_food)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setTitle("Detail")

        val tvNameFood:TextView = findViewById(R.id.tv_detail_name_food)
        val tvAuthor:TextView = findViewById(R.id.tv_detail_author)
        val tvRating:TextView = findViewById(R.id.tv_rating)
        val tvDesc:TextView = findViewById(R.id.tv_detail_desc)
        val tvIngredients:TextView = findViewById(R.id.tv_detail_ingredients)
        val tvMethod:TextView = findViewById(R.id.tv_detail_method)
        val imgFood:ImageView = findViewById(R.id.image_detail_recipe)
        val share:Button = findViewById(R.id.btn_share)
        val seeOnWeb:Button = findViewById(R.id.btn_liat_di_website)

        val recipe = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<Food>(FOOD_RECIPE, Food::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Food>(FOOD_RECIPE)
        }
        if (recipe != null) {
            tvNameFood.text = recipe.name
            tvAuthor.text = recipe.author
            tvDesc.text = recipe.description
            tvRating.text =  "Rating - ${recipe.rating}/5"
            tvIngredients.text = recipe.ingredients.mapIndexed { index, s ->
                "- $s"
            }.joinToString("\n")

            tvMethod.text = recipe.method.mapIndexed { index, s ->
                "${index + 1}. $s"
            }.joinToString("\n")
            sourceUrl = recipe.urls
            Glide.with(this)
                .load(recipe.image)
                .into(imgFood)

        }

        share.setOnClickListener(this)
        seeOnWeb.setOnClickListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_share -> {
                val shareIntent = Intent(Intent.ACTION_SEND).setType("text/plain").putExtra(Intent.EXTRA_TEXT, sourceUrl)
                startActivity(Intent.createChooser(shareIntent, "Share Via"))
            }
            R.id.btn_liat_di_website -> {
                val shareIntent = Intent(Intent.ACTION_VIEW, Uri.parse(sourceUrl))
                startActivity(shareIntent)
            }
        }
    }
}