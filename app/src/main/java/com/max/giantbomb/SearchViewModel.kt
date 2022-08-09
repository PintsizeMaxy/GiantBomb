package com.max.giantbomb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.max.giantbomb.remote.GamesList
import com.max.giantbomb.remote.GiantBombRepository
import com.max.giantbomb.util.LoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val giantBombRepository: GiantBombRepository) :
    ViewModel() {

    private val _searchState = MutableStateFlow<LoadState<GamesList>>(LoadState.InFlight)
    val searchState = _searchState.asStateFlow()


    private var job: Job? = null
    fun queryGames(query: String) {
        job?.cancel()
        job = viewModelScope.launch {
            _searchState.emit(LoadState.InFlight)
            giantBombRepository.getGamesList(query).fold(
                { error ->
                    Timber.e(error.toString())
                    _searchState.emit(LoadState.Failure)
                },
                { games ->
                    _searchState.emit(LoadState.Success(games))
                }
            )
        }
    }
}