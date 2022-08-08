package com.max.giantbomb

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun SearchScreen() {
    val scaffoldState = rememberScaffoldState()
    var query by rememberSaveable { mutableStateOf("") }
    Scaffold(scaffoldState = scaffoldState) { pv ->
        Column(modifier = Modifier.padding(pv)) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.hint_search_game)) },
                value = query,
                onValueChange = {
                    query = it
                }
            )
        }
        LazyColumn{
            items(listOf<String>()){

            }
        }
    }
}