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
import com.example.tproomkotlin.databinding.ActivityMainBinding
import com.example.tproomkotlin.recyclerview.PersonAdapter
import com.example.tproomkotlin.viewmodel.MainViewModel


class MainActivity : AppCompatActivity(), PersonAdapter.PersonAdapterListener {
    private lateinit var binding : ActivityMainBinding
    private var adapter: PersonAdapter? = null
    private val personList: MutableList<PersonneEntity> = ArrayList<PersonneEntity>()
    private lateinit var mViewModel: MainViewModel
    private val checkedIdPersonMap: MutableMap<Int, PersonneEntity> = HashMap<Int, PersonneEntity>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // create/get a view model singleton
        mViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // observe data
        mViewModel.mPersons?.observe(this) { persons ->
            // IMPORTANT : Room replace la list dans LiveModel à chaque changement
            // il faut utiliser un prop container personList
            personList.clear()
            personList.addAll(persons)
            // create a singleton adapter and affect to listView
            if (adapter == null) {
                adapter = PersonAdapter(personList, this)
                binding.recyclerView.layoutManager = LinearLayoutManager(this)
                binding.recyclerView.adapter = adapter
            } else {
                Log.d("App", "changed notified")
                // notify adapter a change
                adapter?.notifyDataSetChanged()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val deleteMenuItem = menu.findItem(R.id.deleteAllData)
        val addAllData = menu.findItem(R.id.addAllData)

        // init visibility
        val checkedItemCounter = checkedIdPersonMap.size
        deleteMenuItem.isVisible = checkedItemCounter > 0
        addAllData.isVisible = checkedItemCounter == 0

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
            refreshRecyclerView()
        }
    }

    private fun refreshMenuItems() {
        invalidateOptionsMenu()

    }

    private fun refreshRecyclerView() {
        if (checkedIdPersonMap.isEmpty()) {
            adapter?.notifyDataSetChanged()
        }
    }
}