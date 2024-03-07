package com.basic.foodrecipessubmissionapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Food(
    val name:String,
    val image: String,
    val urls:String,
    val description:String,
    val author:String,
    val ingredients:List<String>,
    val method:List<String>,
    val rating:Float,
): Parcelable
