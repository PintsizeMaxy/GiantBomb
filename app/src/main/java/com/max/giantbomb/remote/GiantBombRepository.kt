package com.max.giantbomb.remote

import arrow.core.Either
import com.max.giantbomb.cache.CachedGame
import com.max.giantbomb.util.DomainException

interface GiantBombRepository {
    suspend fun getGamesList(game: String): Either<DomainException, List<CachedGame>>
    suspend fun getGame(gameId: String): Either<DomainException, CachedGame>
    suspend fun insertGame(cachedGame: CachedGame)
    suspend fun getViewedGames(): Either<DomainException, List<CachedGame>>
    suspend fun deleteGames()
}