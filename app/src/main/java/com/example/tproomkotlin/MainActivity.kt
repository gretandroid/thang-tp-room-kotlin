package com.example.tproomkotlin

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tproomkotlin.database.PersonneEntity
import com.example.tproomkotlin.database.TestData
import com.example.tproomkotlin.recyclerview.PersonAdapter
import com.example.tproomkotlin.viewmodel.MainViewModel


class MainActivity : AppCompatActivity(), PersonAdapter.PersonAdapterListener {
    private var recyclerView: RecyclerView? = null
    private var adapter: PersonAdapter? = null
    private val personList: MutableList<PersonneEntity> = ArrayList<PersonneEntity>()
    private lateinit var mViewModel: MainViewModel
    private var menu: Menu? = null
    private val checkedIdPersonMap: MutableMap<Int, PersonneEntity> = HashMap<Int, PersonneEntity>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (recyclerView == null) {
            recyclerView = findViewById(R.id.recyclerView)
        }

        // create/get a view model singleton
        mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // observe data
        mViewModel.mPersons?.observe(this) { persons ->
            // IMPORTANT : Room replace la list dans LiveModel à chaque changement
            // il faut utiliser un prop container personList
            personList.clear()
            personList.addAll(persons)
            // create a singleton adapter and affect to listView
            if (adapter == null) {
                adapter = PersonAdapter(personList, this)
                recyclerView!!.layoutManager = LinearLayoutManager(this)
                recyclerView!!.adapter = adapter
            } else {
                Log.d("App", "changed notified")
                // notify adapter a change
                adapter?.notifyDataSetChanged()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menu = menu
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        val deleteMenuItem = menu.findItem(R.id.deleteAllData)
        deleteMenuItem.isVisible = false
        return true
    }

    fun onClickAddAllData(item: MenuItem?) {
        mViewModel.addAllPersons(TestData.getAll())
    }

    fun onClickDeleteAllData(item: MenuItem?) {
        val personList: List<PersonneEntity> = checkedIdPersonMap.values.toList()
        mViewModel.deleteAllPersons(personList)
        checkedIdPersonMap.clear()
        refreshMenuItems()
    }

    override fun onClick(view: View?, person: PersonneEntity?) {
        if (view is ImageView && view.getId() == R.id.iconImageView) {
            val foundPerson: PersonneEntity? = checkedIdPersonMap.get(person?.id)
            if (foundPerson == null) {
                checkedIdPersonMap.put(person!!.id, person)
            }
            if (foundPerson != null) {
                checkedIdPersonMap.remove(person?.id)
            }

            // update Menu item visible
            refreshMenuItems()
        }
    }

    private fun refreshMenuItems() {
        val checkedItemCounter = checkedIdPersonMap.size
        val deleteMenuItem = menu!!.findItem(R.id.deleteAllData)
        val addAllData = menu!!.findItem(R.id.addAllData)
        deleteMenuItem.isVisible = if (checkedItemCounter > 0) true else false
        addAllData.isVisible = if (checkedItemCounter > 0) false else true
        if (checkedItemCounter == 0) {
            adapter?.notifyDataSetChanged()
        }
    }
}