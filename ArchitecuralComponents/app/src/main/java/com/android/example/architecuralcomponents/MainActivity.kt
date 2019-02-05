package com.android.example.architecuralcomponents

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.android.example.architecuralcomponents.adapter.TaskAdapter
import com.android.example.architecuralcomponents.data.Task
import com.android.example.architecuralcomponents.viewModel.TaskViewModel

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var taskViewModel: TaskViewModel
    lateinit var task: Task
    lateinit var taskAdapter: TaskAdapter
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        recyclerView = findViewById(R.id.recyclerview)

        taskAdapter = TaskAdapter(this)

        recyclerView.adapter = taskAdapter
        recyclerView.layoutManager =  LinearLayoutManager(this)

        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel::class.java)

        taskViewModel.listOfTasks?.observe(this, Observer {
            it.let { taskAdapter.setData(it!!) }
        })

        fab.setOnClickListener { view ->
         val intent = Intent(this@MainActivity, NewTaskActivity::class.java)
            startActivityForResult(intent, CODE_REQUESTED)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CODE_REQUESTED && resultCode == Activity.RESULT_OK){
            data.let {
                task = Task(it?.getStringExtra(NewTaskActivity.EXTRA_REPLY)!!)
                taskViewModel.insert(task)
            }
        } else {
           Toast.makeText(applicationContext,R.string.item_not_saved,Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_delete -> {
                taskViewModel.deleteAllTask()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val CODE_REQUESTED = 1
    }
}
