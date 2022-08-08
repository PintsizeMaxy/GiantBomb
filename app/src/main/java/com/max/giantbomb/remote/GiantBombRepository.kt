package com.max.giantbomb.remote

import arrow.core.Either
import com.max.giantbomb.cache.CachedGame
import com.max.giantbomb.util.DomainException

interface GiantBombRepository {
    suspend fun getGamesList(game: String): Either<DomainException, GamesList>
    suspend fun getGame(gameId: String): Either<DomainException, GameData>
    suspend fun insertGame(cachedGame: CachedGame)
}