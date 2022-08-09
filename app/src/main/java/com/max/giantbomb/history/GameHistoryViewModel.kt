package com.max.giantbomb.history

import androidx.lifecycle.ViewModel
import com.max.giantbomb.cache.CachedGame
import com.max.giantbomb.remote.GiantBombRepository
import com.max.giantbomb.util.LoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GameHistoryViewModel @Inject constructor(private val giantBombRepository: GiantBombRepository) :
    ViewModel() {

    private val _viewedGamesState =
        MutableStateFlow<LoadState<List<CachedGame>>>(LoadState.InFlight)
    val viewedGamesState = _viewedGamesState.asStateFlow()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            giantBombRepository.getViewedGames().fold(
                {
                    Timber.e(it.toString())
                    _viewedGamesState.emit(LoadState.Failure)
                },
                {
                    _viewedGamesState.emit(LoadState.Success(it))
                }
            )
        }
    }
}