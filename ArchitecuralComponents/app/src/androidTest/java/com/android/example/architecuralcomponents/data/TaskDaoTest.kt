package com.android.example.architecuralcomponents.data

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class TaskDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var taskDao: TaskDao
    private lateinit var db: TaskDatabase

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getTargetContext()
        db = Room.inMemoryDatabaseBuilder(context, TaskDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        taskDao = db.getTaskDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(IOException::class)
    fun insertAndGetWords() {
        val task = Task("task")
        taskDao.insert(task)
        val allTasks = taskDao.getAlphaberizedWords().waitForValue()
        assertEquals(allTasks[0].task, task.task)
    }

    @Test
    @Throws (IOException::class)
    fun getAllWords() {
        val task1 = Task("task1")
        taskDao.insert(task1)

        val task2 = Task("task2")
        taskDao.insert(task2)

        val allTasks = taskDao.getAlphaberizedWords().waitForValue()
        assertEquals(allTasks.size,2)
        assertEquals(allTasks[0].task,task1.task)
        assertEquals(allTasks[1].task,task2.task)
    }

    @Test
    @Throws (IOException::class)
    fun deleteAllWords() {
        val task1 = Task("task1")
        taskDao.insert(task1)

        taskDao.deleteAllTask()

        val allTasks = taskDao.getAlphaberizedWords().waitForValue()
        assertEquals(allTasks.size,0)
    }
}

@Throws(InterruptedException::class)
fun <T> LiveData<T>.waitForValue(): T {
    val data = arrayOfNulls<Any>(1)
    val latch = CountDownLatch(1)

    val observer = object : Observer<T> {
        override fun onChanged(t: T?) {
            data[0] = t
            latch.countDown()
            this@waitForValue.removeObserver(this)
        }
    }
    this.observeForever(observer)
    latch.await(2, TimeUnit.SECONDS)
    return data[0] as T
}
