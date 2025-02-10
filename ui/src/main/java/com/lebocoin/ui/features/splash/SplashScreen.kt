package com.lebocoin.ui.features.splash

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.lebocoin.domain.model.ErrorType
import com.lebocoin.ui.R

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = hiltViewModel(),
    onError: (errorType: ErrorType?) -> Unit,
    onAnimationFinished: () -> Unit
) {
    val downloadState = viewModel.downloadState.collectAsStateWithLifecycle()
    val errorState = viewModel.errorState.collectAsStateWithLifecycle()
    if(errorState.value.errorType != null) {
        onError.invoke(errorState.value.errorType)
        viewModel.clearError()
    }
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        SplashAnimation()
    }
    if(downloadState.value) {
        LaunchedEffect(Unit) {
            onAnimationFinished.invoke()
        }
    }
}

@Composable
fun SplashAnimation() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash_lottie))

    LottieAnimation(
        composition = composition
    )
}

@Preview(showBackground = true)
@Composable
fun SplashFragmentPreview() {
    SplashAnimation()
}