package com.basic.foodrecipessubmissionapp

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.serialization.json.Json

class MainActivity : AppCompatActivity() {
    private lateinit var rvFood: RecyclerView
    private val list = ArrayList<Food>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvFood = findViewById(R.id.rv_food_menu_list)
        rvFood.setHasFixedSize(true)

        list.addAll(getListRecipes())
        showRecyclerList()
        setTitle("Food Recipe")
        supportActionBar?.elevation = 0f
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this,R.color.white)))
    }
    private fun moveToDetail(food: Food) {
        val moveToDetailIntent = Intent(this@MainActivity, DetailFoodActivity::class.java)
        moveToDetailIntent.putExtra(DetailFoodActivity.FOOD_RECIPE, food)
        startActivity(moveToDetailIntent)
    }

    private fun showRecyclerList() {
        rvFood.layoutManager = LinearLayoutManager(this)
        val listFoodAdapter = ListFoodAdapter(list)
        rvFood.adapter = listFoodAdapter

        listFoodAdapter.setOnItemClickCallback(object : ListFoodAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Food) {
                moveToDetail(data)
            }
        })
    }

    private fun getListRecipes(): ArrayList<Food> {

        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataImg = resources.getStringArray(R.array.data_img)
        val dataUrl = resources.getStringArray(R.array.data_url)
        val dataAuthor = resources.getStringArray(R.array.data_author)
        val dataIngredients = resources.getStringArray(R.array.data_inggree)
        val dataMethod = resources.getStringArray(R.array.data_method)
        val dataRating = resources.getStringArray(R.array.data_rating)

        val listHero = ArrayList<Food>()
        for (i in dataName.indices) {
            val dataArrayIngredients = dataIngredients[i].split(",")
            val dataArrayMethod = dataMethod[i].split(",")
            val hero = Food(dataName[i], dataImg[i], dataUrl[i], dataDescription[i], dataAuthor[i], dataArrayIngredients, dataArrayMethod, dataRating[i].toFloat())
            listHero.add(hero)
        }

        return listHero
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.info -> {
                val aboutIntent = Intent(this@MainActivity, AboutActivity::class.java)
                startActivity(aboutIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}