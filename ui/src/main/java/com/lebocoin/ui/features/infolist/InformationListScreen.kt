package com.lebocoin.ui.features.infolist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lebocoin.domain.model.Information
import com.lebocoin.ui.R
import com.lebocoin.ui.features.infolist.InformationViewModel.PaginationState

@Composable
fun InformationScreen(
    modifier: Modifier = Modifier,
    viewModel: InformationViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.clearPaging()
        viewModel.getInformation()
    }

    val lazyColumnListState = rememberLazyListState()
    val infoList = viewModel.infoList.collectAsStateWithLifecycle()
    val pagingState = viewModel.pagingState.collectAsStateWithLifecycle()

    val shouldPaginate = remember {
        derivedStateOf {
            viewModel.canPaginate && (
                    lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                        ?: -5
                    ) >= (lazyColumnListState.layoutInfo.totalItemsCount - 3)
        }
    }

    LaunchedEffect(key1 = shouldPaginate.value) {
        if (shouldPaginate.value && pagingState.value == PaginationState.REQUEST_INACTIVE) {
            viewModel.getInformation()
        }
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(
            state = lazyColumnListState,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(
                infoList.value.size,
                key = { infoList.value[it].id }
            ){
                InformationCard(infoList.value[it])
            }
        }
    }
}

@Composable
fun InformationCard(data: Information) {
    Card {
        Row(
            modifier = Modifier.fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(imageVector = ImageVector.vectorResource(R.drawable.ic_error), contentDescription = "")
            Text("${data.id} ${data.title}")
        }
    }
    }
