package com.android.example.architecuralcomponents.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "task_table")
data class Task(@PrimaryKey @ColumnInfo(name = "task") val task: String)
