package ru.netology.nmedia.db

object PostsTable {
    enum class Column(val columnName: String) {
        ID("id"),
        AUTHOR("author"),
        CONTENT("content"),
        PUBLISHED("published"),
        LIKED_BY_ME("likedByMe"),
        LIKES("likes"),
        SHARES("shares"),
        VIEWS("views"),
        VIDEO_CONTENT("videoContent")
    }

    const val NAME = "posts"
    val ALL_COLUMNS_NAMES = Column.values().map { it.columnName }.toTypedArray()

    val DDl = """
        CREATE TABLE $NAME(
        ${Column.ID.columnName} INTEGER PRIMARY KEY AUTOINCREMENT,
        ${Column.AUTHOR.columnName} TEXT NOT NULL,
        ${Column.CONTENT.columnName} TEXT NOT NULL,
        ${Column.PUBLISHED.columnName} TEXT NOT NULL,        
        ${Column.LIKED_BY_ME.columnName} BOOLEAN NOT NULL DEFAULT 0,        
        ${Column.LIKES.columnName} INTEGER NOT NULL DEFAULT 0, 
        ${Column.SHARES.columnName} INTEGER NOT NULL DEFAULT 0,
        ${Column.VIEWS.columnName} INTEGER NOT NULL DEFAULT 0,
        ${Column.VIDEO_CONTENT.columnName} TEXT DEFAULT NULL       
        );
        """.trimIndent()

}