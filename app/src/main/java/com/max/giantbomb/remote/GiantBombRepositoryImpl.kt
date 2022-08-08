package com.max.giantbomb.remote

import arrow.core.Either
import com.max.giantbomb.cache.CachedGame
import com.max.giantbomb.cache.GameDao
import com.max.giantbomb.util.DomainException

class GiantBombRepositoryImpl(
    private val gameService: GiantBombClient,
    private val gameDao: GameDao
): GiantBombRepository {
    override suspend fun getGamesList(game: String): Either<DomainException, GamesList> {
        return Either.catch {
            gameService.getGamesList("9d45436f87d3848d2bdcce810bacb6df57dfd134", "name:$game")
        }.mapLeft { DomainException.ServerError(it.toString()) }
    }

    override suspend fun getGame(gameId: String): Either<DomainException, GameData> {
        TODO("Not yet implemented")
    }

    override suspend fun insertGame(cachedGame: CachedGame) {
        TODO("Not yet implemented")
    }
}