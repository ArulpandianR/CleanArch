package com.t2s.task.helper

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.t2s.task.data.TaskRepo


class DataConverter {
    @TypeConverter
    fun getTaskFromJson(value: String): List<TaskRepo> {
        val gson = Gson()
        val type = object : TypeToken<List<TaskRepo>>() {}.type
        return gson.fromJson(value, type)
    }
}