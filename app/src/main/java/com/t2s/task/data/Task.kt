package com.t2s.task.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "tasks")
data class Task(
    @ColumnInfo(name = "id") var id: Double,
    @ColumnInfo(name = "name") var name: String? = "",
    @ColumnInfo(name = "price") var price: String? = "",
    @PrimaryKey @ColumnInfo(name = "task_id") var taskId: String = UUID.randomUUID().toString()
)