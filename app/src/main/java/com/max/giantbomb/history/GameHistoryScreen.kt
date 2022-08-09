package com.max.giantbomb.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.max.giantbomb.R
import com.max.giantbomb.ui.theme.regular
import com.max.giantbomb.util.*


// Show screen with all the previously selected games clicked on from the search screen
@Composable
fun GameHistoryScreen(
    viewModel: GameHistoryViewModel,
    onBack: () -> Unit,
    navigateToDetails: (String) -> Unit
) {
    val state = viewModel.viewedGamesState.collectAsState()
    val scaffoldState = rememberScaffoldState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    Scaffold(scaffoldState = scaffoldState, topBar = {
        TopAppBarWithBackstack(
            title = stringResource(R.string.viewed_games),
            onBack = onBack,
            actions = {
                DeleteAction {
                    showDeleteDialog = true
                }
            }
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
        if (showDeleteDialog) {
            DeleteDialog(clearData = { viewModel.clearHistory() }) {
                showDeleteDialog = false
            }
        }
    }
}

// Display to the user a delete dialog to prevent an accident mis-click of the delete button on toolbar
@Composable
fun DeleteDialog(clearData: () -> Unit, onClose: () -> Unit) {
    AlertDialog(
        onDismissRequest = onClose,
        title = { Text(stringResource(R.string.delete_records)) },
        text = { Text(stringResource(R.string.permanent_action)) },
        confirmButton = {
            TextButton(onClick = {
                clearData()
                onClose()
            }) {
                Text(text = stringResource(R.string.delete), color = MaterialTheme.colors.error)
            }
        },
        dismissButton = {
            TextButton(onClick = { onClose() }) {
                Text(text = stringResource(R.string.cancel))
            }
        }
    )

}