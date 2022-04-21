package com.example.tproomkotlin.reposistory

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.tproomkotlin.database.AppDatabase
import com.example.tproomkotlin.database.PersonneDao
import com.example.tproomkotlin.database.PersonneEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.stream.Collectors
// communicate avec la DB
class AppReposistory private constructor(context: Context) {
    private val database: AppDatabase? = AppDatabase.getInstance(context)

    fun getAllPersons() : LiveData<List<PersonneEntity>>? {
        val dao: PersonneDao? = database?.personneDao()
        return dao?.getAll()
    }

    fun addAllPersons(persons: List<PersonneEntity>?) {
        CoroutineScope(Dispatchers.IO).launch {
            database?.personneDao()?.insertAll(persons)
        }
    }

    fun deleteAll() {
        CoroutineScope(Dispatchers.IO).launch {
            database?.personneDao()?.deleteAll()
        }
    }

    fun deleteAllPersons(persons: List<PersonneEntity>?) {
        val idList = persons?.map{it.id}
        CoroutineScope(Dispatchers.IO).launch {
            database?.personneDao()?.deleteAllPersons(idList)
        }
    }

    companion object {
        private var instance: AppReposistory? = null
        fun getInstance(context: Context): AppReposistory? {
            if (instance == null) {
                instance = AppReposistory(context)
            }
            return instance
        }
    }

}


