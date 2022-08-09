package com.max.giantbomb.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CachedGame::class], version = 1)
abstract class GameDatabase : RoomDatabase(){
    abstract fun gameDao(): GameDao
}