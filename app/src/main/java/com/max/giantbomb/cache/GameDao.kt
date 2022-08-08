package com.max.giantbomb.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GameDao {
    @Query("SELECT * FROM GameData WHERE gameId = :gameId")
    fun getCachedGame(gameId: String): CachedGame

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGame(cachedGame: CachedGame)
}