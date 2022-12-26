package com.note.app.myfirstapp.nearplaces.models

data class Location(
    val address: String,
    val country: String,
    val cross_street: String,
    val formatted_address: String,
    val locality: String,
    val postcode: String,
    val region: String
)