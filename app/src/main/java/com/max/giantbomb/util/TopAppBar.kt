package com.max.giantbomb.util

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight

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

@Composable
fun TopBarTextButton(
    label: Int,
    onClick: () -> Unit
){
    TextButton(onClick = onClick) {
        Text(stringResource(label), color = Color.White)
    }
}