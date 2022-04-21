package com.example.tproomkotlin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.tproomkotlin.database.PersonneEntity
import com.example.tproomkotlin.reposistory.AppReposistory


// car on a besoin un context pour reposistory donc
// on extends AndroidViewModel au lieu de ViewModel
class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val reposistory: AppReposistory?
    var mPersons: LiveData<List<PersonneEntity>>?

    init {
        reposistory = AppReposistory.getInstance(application.applicationContext)
        mPersons = reposistory?.getAllPersons()
    }

    fun addAllPersons(persons: List<PersonneEntity>?) {
        reposistory?.addAllPersons(persons)
    }

    fun deleteAllPersons(persons: List<PersonneEntity>?) {
        reposistory?.deleteAllPersons(persons)
    }


}