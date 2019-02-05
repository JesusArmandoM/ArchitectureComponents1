package com.android.example.architecuralcomponents.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.android.example.architecuralcomponents.data.Repository
import com.android.example.architecuralcomponents.data.Task
import com.android.example.architecuralcomponents.data.TaskDao
import com.android.example.architecuralcomponents.data.TaskDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class TaskViewModel (val context: Application) : AndroidViewModel(context) {

    lateinit var repository : Repository
    lateinit var taskDao : TaskDao


    private var job = Job()
    private val coroutineContext :CoroutineContext
    get() = job + Dispatchers.Main

    var coroutineScope = CoroutineScope(coroutineContext)

    var listOfTasks: LiveData<List<Task>>? = null

    init {
        taskDao = TaskDatabase.getDatabase(context = context).getTaskDao()
        repository = Repository(taskDao)

        listOfTasks = repository.listOftasks
    }

    fun insert (task: Task) = coroutineScope.launch(Dispatchers.IO){
        repository.insert(task)
    }

    fun deleteAllTask() = coroutineScope.launch(Dispatchers.IO) {
        repository.deleteAllTask()
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}