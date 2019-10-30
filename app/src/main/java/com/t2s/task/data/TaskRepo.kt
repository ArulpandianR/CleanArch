package com.t2s.task.data

import com.google.gson.annotations.SerializedName


data class TaskRepo(
    @SerializedName("id") var id: Double,
    @SerializedName("name") var name: String,
    @SerializedName("price") var price: String
)