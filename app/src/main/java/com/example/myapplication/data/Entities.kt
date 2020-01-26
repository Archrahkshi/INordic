package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)

data class Weather(
    val base: String,
    val name: String
)

@Entity
data class Person(
    val firstName: String,
    val lastName: String,
    val age: Int,

    @PrimaryKey
    var id: String = UUID.randomUUID().toString()
)