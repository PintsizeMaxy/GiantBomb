package com.max.giantbomb.remote

import arrow.core.Either
import com.max.giantbomb.cache.CachedGame
import com.max.giantbomb.cache.GameDao
import com.max.giantbomb.util.DomainException
import timber.log.Timber

class GiantBombRepositoryImpl(
    private val gameService: GiantBombClient,
    private val gameDao: GameDao
) : GiantBombRepository {
    override suspend fun getGamesList(game: String): Either<DomainException, GamesList> {
        return Either.catch {
            gameService.getGamesList("9d45436f87d3848d2bdcce810bacb6df57dfd134", "name:$game")
        }.mapLeft { DomainException.ServerError(it.toString()) }
    }

    override suspend fun getGame(gameId: String): Either<DomainException, CachedGame> {
        return if (gameDao.gameDataEntityExists(gameId)) {
            Timber.d("Game retrieved from cache")
            Either.catch { gameDao.getCachedGame(gameId) }
                .mapLeft { DomainException.CacheError(it.message) }
        } else {
            Either.catch {
                val result =
                    gameService.getGameDetails(gameId, "9d45436f87d3848d2bdcce810bacb6df57dfd134")
                CachedGame(
                    result.id.orEmpty(),
                    result.title.orEmpty(),
                    result.image?.imageUrl.orEmpty(),
                    result.description.orEmpty()
                ).also {
                    insertGame(it)
                }
            }.mapLeft { DomainException.ServerError(it.message) }
        }
    }

    override suspend fun insertGame(cachedGame: CachedGame) {
        if(!gameDao.gameDataEntityExists(cachedGame.gameId)) {
            gameDao.insertGame(cachedGame)
        }
    }
}