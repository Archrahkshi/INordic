package com.example.myapplication.data

import androidx.room.*

@Dao
interface PeopleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPeople(person: Person): Long

    @Delete
    fun deletePeople(person: Person)

    @Query("SELECT * FROM person ORDER BY person.age DESC")
    fun getAll(): List<Person>

    @Query("SELECT * FROM person WHERE person.age >= 18")
    fun getAllAdults(): List<Person>

    @Query("SELECT * FROM person WHERE person.age > :age")
    fun getAgeGreaterThan(age: Int): List<Person>

    @Query("SELECT * FROM person WHERE person.age BETWEEN :age1 AND :age2")
    fun getAgeBetween(age1: Int, age2: Int): List<Person>

    @Query("SELECT AVG(person.age) FROM person")
    fun getAverageAge(): Double
}