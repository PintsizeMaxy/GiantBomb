package com.max.giantbomb.remote

import android.net.Uri
import arrow.core.Either
import com.max.giantbomb.cache.CachedGame
import com.max.giantbomb.cache.GameDao
import com.max.giantbomb.util.DomainException
import com.max.giantbomb.util.DomainException.*
import timber.log.Timber

class GiantBombRepositoryImpl(
    private val gameService: GiantBombClient,
    private val gameDao: GameDao
) : GiantBombRepository {
    override suspend fun getGamesList(game: String): Either<DomainException, List<CachedGame>> {
        return Either.catch {
            gameService.getGamesList("9d45436f87d3848d2bdcce810bacb6df57dfd134", "name:$game").results.map {
                CachedGame(it.id.orEmpty(), it.title.orEmpty(), Uri.parse(it.image?.imageUrl.orEmpty()).toString(), it.description.orEmpty())
            }
        }.mapLeft { ServerError(it.toString()) }
    }

    override suspend fun getGame(gameId: String): Either<DomainException, CachedGame> {
        return if (gameDao.gameDataEntityExists(gameId)) {
            Timber.d("Game retrieved from cache")
            Either.catch { gameDao.getCachedGame(gameId) }
                .mapLeft { CacheError(it.message) }
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
            }.mapLeft { ServerError(it.message) }
        }
    }

    override suspend fun insertGame(cachedGame: CachedGame) {
        if(!gameDao.gameDataEntityExists(cachedGame.gameId)) {
            gameDao.insertGame(cachedGame)
        }
    }

    override suspend fun getViewedGames(): Either<DomainException, List<CachedGame>> {
        return Either.catch{
            gameDao.getViewedGames()
        }.mapLeft { CacheError(it.message) }
    }

    override suspend fun deleteGames() {
        gameDao.deleteRecords()
    }
}