package com.android.example.architecuralcomponents.data

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread

class Repository(val dao : TaskDao) {

    var listOftasks:LiveData<List<Task>> = dao.getAlphaberizedWords()

    @WorkerThread
    fun insert (task: Task) = dao.insert(task)

    @WorkerThread
    fun deleteAllTask() = dao.deleteAllTask()
}