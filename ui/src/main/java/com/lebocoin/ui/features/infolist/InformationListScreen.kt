package com.lebocoin.ui.features.infolist

import android.webkit.WebSettings
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.lebocoin.domain.model.ErrorType
import com.lebocoin.domain.model.Information
import com.lebocoin.ui.R
import com.lebocoin.ui.components.ErrorComponent
import com.lebocoin.ui.features.infolist.InformationViewModel.PaginationState
import com.lebocoin.ui.navigation.NavigationItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformationScreen(
    modifier: Modifier = Modifier,
    viewModel: InformationViewModel = hiltViewModel(),
    onError: (errorType: ErrorType?) -> Unit,
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.clearPaging()
        viewModel.getInformation()
    }

    val lazyColumnListState = rememberLazyListState()
    val infoList = viewModel.infoList.collectAsStateWithLifecycle()
    val pagingState = viewModel.pagingState.collectAsStateWithLifecycle()
    val errorState = viewModel.errorState.collectAsStateWithLifecycle()

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

    if(errorState.value.errorType != null) {
        onError.invoke(errorState.value.errorType)
        viewModel.clearError()
    }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_leboncoin_icon),
                        contentDescription = stringResource(R.string.app_name)
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            LazyColumn(
                state = lazyColumnListState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            ) {
                items(
                    infoList.value.size,
                    key = { infoList.value[it].id }
                ) {
                    InformationCard(infoList.value[it])
                    Spacer(Modifier.height(8.dp))
                }
            }
        }

    }
}

@Composable
fun InformationCard(data: Information) {
    val context = LocalContext.current
    Card {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 16.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val imageUrl = data.thumbnailUrl
            val placeholderImage = R.drawable.ic_image_placeholder
            val imageRequest = ImageRequest.Builder(context)
                .data(imageUrl)
                .memoryCacheKey(imageUrl)
                .diskCacheKey(imageUrl)
                .placeholder(placeholderImage)
                .error(placeholderImage)
                .fallback(placeholderImage)
                .diskCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .transformations(CircleCropTransformation())
                .build()


            AsyncImage(
                model = imageRequest,
                placeholder = painterResource(R.drawable.ic_image_placeholder),
                error = painterResource(R.drawable.ic_image_placeholder),
                fallback = painterResource(R.drawable.ic_image_placeholder),
                contentDescription = data.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape),
            )
            Spacer(Modifier.width(8.dp))
            Text(data.title)
        }
    }
}
