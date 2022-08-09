package com.max.giantbomb.search

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.max.giantbomb.R
import com.max.giantbomb.remote.GameData
import com.max.giantbomb.ui.theme.large
import com.max.giantbomb.ui.theme.regular
import com.max.giantbomb.ui.theme.small
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
                    if(result.data.results.isEmpty()){
                        Text(text = stringResource(R.string.no_results), modifier = Modifier.padding(regular), style = MaterialTheme.typography.h4)
                    } else {
                        LazyColumn {
                            items(result.data.results) {
                                GameCard(game = it) {
                                    viewModel.cacheGame(it)
                                    navigateToDetails(it.id.orEmpty())
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}

@Composable
private fun GameCard(game: GameData, onClick: () -> Unit) {
    Card(modifier = Modifier
        .padding(regular)
        .clickable { onClick() }, elevation = 2.dp) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = large, top = small, bottom = small),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(end = regular),
                text = game.title.orEmpty(),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.h6
            )
            AsyncImage(
                modifier = Modifier.size(82.dp),
                model = Uri.parse(game.image?.imageUrl.orEmpty()), contentDescription = null,
                placeholder = painterResource(R.drawable.ic_placeholder),
                error = painterResource(R.drawable.ic_error_image),

                )
        }
    }
}