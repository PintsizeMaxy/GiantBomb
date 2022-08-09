package com.max.giantbomb.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.max.giantbomb.R
import com.max.giantbomb.ui.theme.regular
import com.max.giantbomb.util.*

@Composable
fun GameHistoryScreen(viewModel: GameHistoryViewModel, onBack : () -> Unit, navigateToDetails: (String) -> Unit) {
    val state = viewModel.viewedGamesState.collectAsState()
    val scaffoldState = rememberScaffoldState()
    Scaffold(scaffoldState = scaffoldState, topBar = {
        TopAppBarWithBackstack(
            title = stringResource(R.string.viewed_games),
            onBack = onBack
        )
    }) { pv ->
        Column(modifier = Modifier.padding(pv)) {
            when (val result = state.value) {
                LoadState.Failure -> ErrorText()
                LoadState.InFlight -> CircularLoader()
                is LoadState.Success -> {
                    if (result.data.isEmpty()) {
                        Text(
                            text = stringResource(R.string.no_results), modifier = Modifier.padding(
                                regular
                            ), style = MaterialTheme.typography.h4
                        )
                    } else {
                        LazyColumn {
                            items(result.data) {
                                GameCard(game = it) {
                                    navigateToDetails(it.gameId)
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}