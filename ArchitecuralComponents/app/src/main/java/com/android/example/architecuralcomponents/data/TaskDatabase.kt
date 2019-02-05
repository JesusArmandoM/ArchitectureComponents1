package com.android.example.architecuralcomponents.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(version = 1, entities = [Task::class])
abstract class TaskDatabase : RoomDatabase() {

    abstract fun getTaskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE:TaskDatabase? = null

        fun getDatabase(context: Context) : TaskDatabase {
            return INSTANCE ?: synchronized(this) {
                    val temporayInstance = Room.databaseBuilder(
                        context.applicationContext,
                        TaskDatabase::class.java,
                        "TaskDatabase"
                    ).build()
                    INSTANCE = temporayInstance
                     temporayInstance
            }
        }
      }
}



