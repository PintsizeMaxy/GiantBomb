package com.max.giantbomb.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.max.giantbomb.R
import com.max.giantbomb.ui.theme.regular
import com.max.giantbomb.util.*

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    navigateToDetails: (String) -> Unit,
    navigateToHistory: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val state = viewModel.searchState.collectAsState()
    var query by rememberSaveable { mutableStateOf("") }
    Scaffold(scaffoldState = scaffoldState, topBar = {
        TopAppBarHome(title = stringResource(R.string.app_name), actions = {
            TopBarTextButton(R.string.history, navigateToHistory)
        })
    }) { pv ->
        Column(modifier = Modifier.padding(pv)) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.hint_search_game)) },
                value = query,
                onValueChange = {
                    query = it
                    viewModel.queryGames(query)
                }
            )
            when (val result = state.value) {
                LoadState.Failure -> ErrorText()
                LoadState.InFlight -> CircularLoader()
                is LoadState.Success -> {
                    if(result.data.isEmpty()){
                        Text(text = stringResource(R.string.no_results), modifier = Modifier.padding(regular), style = MaterialTheme.typography.h4)
                    } else {
                        LazyColumn {
                            items(result.data) {
                                GameCard(game = it) {
                                    viewModel.cacheGame(it)
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