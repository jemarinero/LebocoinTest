package com.lebocoin.ui.features.infolist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lebocoin.domain.model.Information
import com.lebocoin.ui.components.ErrorComponent
import com.lebocoin.ui.components.LoadingComponent

@Composable
fun InformationScreen(
    modifier: Modifier = Modifier,
    viewModel: InformationViewModel = hiltViewModel()
) {
    val state = viewModel.state
    viewModel.getInformation()

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LoadingComponent(state.isLoading)
        ErrorComponent(state.isError)
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                state.data.forEach {
                    InformationCard(it)
                }

            }
        }
    }
}

@Composable
fun InformationCard(data: Information) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("${data.id} ${data.title}")
    }
}