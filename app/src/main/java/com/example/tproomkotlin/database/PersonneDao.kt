package com.example.tproomkotlin.database

import androidx.lifecycle.LiveData

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PersonneDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersonne(personneEntity: PersonneEntity?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(personnes: List<PersonneEntity>?)

    @Delete
    suspend fun deletePersonne(personneEntity: PersonneEntity?)

    @Query("SELECT * FROM person WHERE id=:id")
    suspend fun getPersonneById(id: Int): PersonneEntity?

    @Query("SELECT * FROM person ORDER BY date DESC")
    fun getAll (): LiveData<List<PersonneEntity>>?

    @Query("DELETE FROM person")
    suspend fun deleteAll(): Int

    @Query("DELETE FROM person where id in (:idList)")
    suspend fun deleteAllPersons(idList: List<Int>?): Int

    @Query("SELECT count(*) FROM person")
    suspend fun count(): Int
}