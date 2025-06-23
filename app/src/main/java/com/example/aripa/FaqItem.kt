package com.example.aripa

data class FaqItem(
    val id: String,
    val question: String,
    val answer: String,
    var isExpanded: Boolean = false // To track expansion state
)