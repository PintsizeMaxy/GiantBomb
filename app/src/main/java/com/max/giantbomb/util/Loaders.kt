package com.max.giantbomb.util

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.max.giantbomb.ui.theme.large

@Composable
fun ColumnScope.CircularLoader(){
    CircularProgressIndicator(
        modifier = Modifier
            .padding(large)
            .align(Alignment.CenterHorizontally)
    )
}