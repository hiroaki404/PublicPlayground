package com.example.publicplayground.shared_element_transition

import com.example.publicplayground.R

data class Bird(
    val id: Int,
    val name: String,
    val imageResId: Int
)

val birds = listOf(
    Bird(1, "スズメ", R.drawable.suzume),
    Bird(2, "カラス", R.drawable.karasu),
    Bird(3, "ハト", R.drawable.hato),
    Bird(4, "シマエナガ", R.drawable.shimaenaga),
    Bird(5, "メジロ", R.drawable.mejiro),
)
