package com.android.example.architecuralcomponents.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface TaskDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(task: Task)

    @Query ("select* from task_table order by task ASC")
    fun getAlphaberizedWords(): LiveData<List<Task>>

    @Query ("delete from task_table")
    fun deleteAllTask()

}