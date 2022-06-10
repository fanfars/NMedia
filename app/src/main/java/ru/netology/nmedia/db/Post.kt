package ru.netology.nmedia.db

import ru.netology.nmedia.dto.Post

internal fun PostEntity.toModel() = Post(
    id = id,
    author = author,
    content = content,
    published = published,
    likes = likes,
    shares = shares,
    views = views,
    likedByMe = likedByMe,
    videoContent = videoContent
)

internal fun Post.toEntity() = PostEntity(
    id = id,
    author = author,
    content = content,
    published = published,
    likes = likes,
    shares = shares,
    views = views,
    likedByMe = likedByMe,
    videoContent = videoContent
)