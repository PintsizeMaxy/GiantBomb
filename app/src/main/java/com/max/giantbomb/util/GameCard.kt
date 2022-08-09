package com.max.giantbomb.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.max.giantbomb.R
import com.max.giantbomb.cache.CachedGame
import com.max.giantbomb.ui.theme.large
import com.max.giantbomb.ui.theme.regular
import com.max.giantbomb.ui.theme.small

@Composable
fun GameCard(game: CachedGame, onClick: () -> Unit) {
    Card(modifier = Modifier
        .padding(regular)
        .clickable { onClick() }, elevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = large, top = small, bottom = small),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(end = regular),
                text = game.name,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.h6
            )
            AsyncImage(
                modifier = Modifier.size(82.dp),
                model = game.url, contentDescription = null,
                placeholder = painterResource(R.drawable.ic_placeholder),
                error = painterResource(R.drawable.ic_error_image)
            )
        }
    }
}