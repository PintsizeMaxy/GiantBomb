package com.max.giantbomb.util

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.max.giantbomb.R
import com.max.giantbomb.ui.theme.large

@Composable
fun ErrorText(){
    Text(
        stringResource(R.string.error_retrieving_data),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.h5,
        modifier = Modifier.padding(large)
    )
}