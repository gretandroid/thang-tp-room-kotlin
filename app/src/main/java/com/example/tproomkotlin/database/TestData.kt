package com.example.tproomkotlin.database

import java.text.SimpleDateFormat
import java.util.*


object TestData {
    var formatter = SimpleDateFormat("dd/mm/yyyy", Locale.getDefault())
    fun getAll() = arrayListOf<PersonneEntity>(
        PersonneEntity(formatter.parse("10/02/2021"), "nom1"),
        PersonneEntity(formatter.parse("10/02/2021"), "nom2"),
        PersonneEntity(formatter.parse("10/02/2021"), "nom3"),
        PersonneEntity(formatter.parse("10/02/2021"), "nom4")
    )
}
