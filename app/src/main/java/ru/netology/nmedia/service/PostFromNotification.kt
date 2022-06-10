package ru.netology.nmedia.service

import com.google.gson.annotations.SerializedName

class PostFromNotification(

    @SerializedName("content")
    val content: String,

    @SerializedName("postId")
    val postId: Long,

    @SerializedName("postAuthor")
    val postAuthor: String
)