package com.example.tasks_sheungkit

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasks_sheungkit.adapter.TaskAdapter
import com.example.tasks_sheungkit.databinding.ActivityMainBinding
import com.example.tasks_sheungkit.models.Task
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var myAdapter: TaskAdapter
    lateinit var sharedPreferences: SharedPreferences
    var tasksList:MutableList<Task> = mutableListOf()

    val deleteItemFromList = {
        rowNumber:Int ->
        val removedTask = tasksList.removeAt(rowNumber)
        saveTasksToSharedPreferences()
        myAdapter.notifyDataSetChanged()
        val snackbar = Snackbar.make(binding.root, "Deleted ${removedTask.name}", Snackbar.LENGTH_LONG)
        snackbar.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.myToolbar)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        myAdapter = TaskAdapter(tasksList, deleteItemFromList)
        binding.rv.adapter = myAdapter
        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )

        loadTasksFromSharedPreferences()
        myAdapter.notifyDataSetChanged()

        binding.btnAdd.setOnClickListener {
            addTask()
        }
    }

    private fun saveTasksToSharedPreferences(){
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(tasksList)
        editor.putString("tasksList", json)
        editor.apply()
    }
    private fun loadTasksFromSharedPreferences(){
        val gson = Gson()
        val json = sharedPreferences.getString("tasksList", null)
        val type = object : TypeToken<MutableList<Task>>(){}.type
        if (json != null){
            tasksList.clear()
            tasksList.addAll(gson.fromJson(json, type))
        }
    }

//////////////////////////////Testing use ///////////////////////////////////////
//    private fun resetSharedPreferences(){
//        val editor = sharedPreferences.edit()
//        editor.clear().apply()
//        tasksList.clear()
//        tasksList.addAll(
//            listOf(
//                Task("Watch movie", false),
//                Task("Buy groceries", true),
//                Task("Finish cleaning bathroom", false),
//                Task("See doctor", false),
//                Task("Do homework", true)
//            )
//        )
//        saveTasksToSharedPreferences()
//        myAdapter.notifyDataSetChanged()
//    }
//////////////////////////////////////////////////////////////////

    private fun addTask(){
        val nameFromUI:String = binding.etName.text.toString()
        val isPriorityFromUI:Boolean = binding.swIsPriority.isChecked
        val taskToAdd: Task = Task(nameFromUI, isPriorityFromUI)
        tasksList.add(taskToAdd)
        saveTasksToSharedPreferences()
        myAdapter.notifyDataSetChanged()
        val snackbar = Snackbar.make(binding.root, "Adding ${taskToAdd.name}", Snackbar.LENGTH_LONG)
        snackbar.show()
        binding.etName.setText("")
        binding.swIsPriority.isChecked = false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.setting_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.delete_all_tab -> {
                tasksList.clear()
                saveTasksToSharedPreferences()
                myAdapter.notifyDataSetChanged()
                var snackbar = Snackbar.make(binding.root, "All tasks deleted", Snackbar.LENGTH_LONG)
                snackbar.show()
                true
            }
///////////////////Testing use////////////////
//            R.id.reset_all -> {
//                resetSharedPreferences()
//                true
/////////////////////////////////////////////
//            }
        else -> super.onOptionsItemSelected(item)
        }
    }
}