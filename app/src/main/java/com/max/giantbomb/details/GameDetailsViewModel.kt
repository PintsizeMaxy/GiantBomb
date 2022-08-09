package com.max.giantbomb.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class GameDetailsViewModel @Inject constructor(
    private val giantBombRepository: GiantBombRepository,
    private val savedStateHandle: SavedStateHandle
) :
    ViewModel() {

    private val _gameDetailsState = MutableStateFlow<LoadState<CachedGame>>(LoadState.InFlight)
    val gameDetailsState = _gameDetailsState.asStateFlow()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val id = savedStateHandle.get<String>("gameId").orEmpty()
            giantBombRepository.getGame(id).fold(
                {
                    Timber.e(it.toString())
                    _gameDetailsState.emit(LoadState.Failure)
                },
                {
                    _gameDetailsState.emit(LoadState.Success(it))
                }
            )
        }
    }
}