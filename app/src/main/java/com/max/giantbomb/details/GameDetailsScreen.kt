package com.max.giantbomb.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT
import coil.compose.AsyncImage
import com.max.giantbomb.R
import com.max.giantbomb.ui.theme.regular
import com.max.giantbomb.util.CircularLoader
import com.max.giantbomb.util.ErrorText
import com.max.giantbomb.util.LoadState
import com.max.giantbomb.util.TopAppBarWithBackstack

@Composable
fun GameDetailsScreen(viewModel: GameDetailsViewModel, onBack: () -> Unit) {
    val state = viewModel.gameDetailsState.collectAsState()
    val scaffoldState = rememberScaffoldState()
    Scaffold(scaffoldState = scaffoldState, topBar = {
        TopAppBarWithBackstack(
            title = when (val header = state.value) {
                is LoadState.Success -> header.data.name
                LoadState.InFlight -> ""
                else -> stringResource(R.string.app_name)
            },
            onBack = onBack
        )
    }) { pv ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pv)
        ) {
            when (val game = state.value) {
                LoadState.Failure -> ErrorText()
                LoadState.InFlight -> CircularLoader()
                is LoadState.Success -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        AsyncImage(
                            modifier = Modifier.size(128.dp),
                            model = game.data.url, contentDescription = null,
                            placeholder = painterResource(R.drawable.ic_placeholder),
                            error = painterResource(R.drawable.ic_error_image),
                            alignment = Alignment.Center
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            HtmlCompat.fromHtml(game.data.description, FROM_HTML_MODE_COMPACT)
                                .toString().ifEmpty {
                                    stringResource(
                                        R.string.no_description
                                    )
                                },
                            modifier = Modifier.padding(start = regular, end = regular)
                        )
                    }
                }
            }
        }
    }
}