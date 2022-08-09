package com.max.giantbomb.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "GameData")
data class CachedGame(
    @PrimaryKey var gameId: String,
    var name: String,
    var url: String,
    var description: String
)
