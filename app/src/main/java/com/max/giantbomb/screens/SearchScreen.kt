package com.max.giantbomb.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.max.giantbomb.R
import com.max.giantbomb.SearchViewModel
import com.max.giantbomb.remote.GameData
import com.max.giantbomb.ui.theme.large
import com.max.giantbomb.ui.theme.small
import com.max.giantbomb.util.CircularLoader
import com.max.giantbomb.util.ErrorText
import com.max.giantbomb.util.LoadState

@Composable
fun SearchScreen(viewModel: SearchViewModel) {
    val scaffoldState = rememberScaffoldState()
    val state = viewModel.searchState.collectAsState()
    var query by rememberSaveable { mutableStateOf("") }
    Scaffold(scaffoldState = scaffoldState) { pv ->
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
                    LazyColumn {
                        items(result.data.results) {
                            GameCard(game = it)
                        }
                    }
                }
            }
            
        }
    }
}

@Composable
private fun GameCard(game: GameData) {
    Card(modifier = Modifier.padding(bottom = small), elevation = 2.dp) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = large, top = small, bottom = small)
        ) {
            Text(
                text = game.title, modifier = Modifier
                    .fillMaxWidth()
                    .padding(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h6
            )
        }
    }
    Divider()
}