package com.t2s.task.data


import com.google.gson.annotations.SerializedName

data class TaskResponse(
    @SerializedName("data")
    var compressTask: MutableList<String>
)