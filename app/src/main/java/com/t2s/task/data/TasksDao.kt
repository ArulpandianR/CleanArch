package com.t2s.task.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TasksDao {

    @Query("SELECT * FROM Tasks ORDER BY name")
    fun getTasks(): List<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTask(task: Task): Long

    @Query("DELETE FROM Tasks")
    fun deleteTasks()

    @Query("SELECT COUNT(*) FROM Tasks")
    fun getRowCount(): Int
}