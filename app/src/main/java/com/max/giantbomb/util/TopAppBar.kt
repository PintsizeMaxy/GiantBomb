package com.max.giantbomb.util

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.max.giantbomb.R

// Defines the TopAppBar for the main screen that the user lands on
@Composable
fun TopAppBarHome(
    title: String,
    actions: @Composable RowScope.() -> Unit
) {
    TopAppBar(
        title = { Text(title, fontWeight = FontWeight.Light) },
        actions = actions
    )
}

// Defines TopAppBar where you can pop the backstack, optional actions
@Composable
fun TopAppBarWithBackstack(
    title: String,
    onBack: () -> Unit,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = { Text(text = title, fontWeight = FontWeight.Light)},
        navigationIcon = { BackArrow(onBack) },
        actions = actions
    )
}

// Defines a text button to be display in toolbar if needed
@Composable
fun TopBarTextButton(
    label: Int,
    onClick: () -> Unit
){
    TextButton(onClick = onClick) {
        Text(stringResource(label), color = Color.White)
    }
}


// Display back arrow in toolbar if backstack exists on current screen
@Composable
fun BackArrow(onBack: () -> Unit) {
    IconButton(onClick = onBack) {
        Icon(Icons.Filled.ArrowBack, stringResource(R.string.back))
    }
}

// Display a delete icon where you can clear data
@Composable
fun DeleteAction(onDelete: () -> Unit) {
    IconButton(onClick = onDelete) {
        Icon(Icons.Filled.Delete, stringResource(R.string.delete))
    }
}