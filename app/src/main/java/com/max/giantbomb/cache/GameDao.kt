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

    @Query("SELECT EXISTS (SELECT * FROM GameData WHERE gameId = :gameId)")
    fun gameDataEntityExists(gameId: String): Boolean

    @Query("SELECT * FROM GameData")
    fun getViewedGames(): List<CachedGame>

    @Query("DELETE FROM GameData")
    fun deleteRecords()
}