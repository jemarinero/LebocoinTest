package com.lebocoin.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lebocoin.domain.model.ErrorType
import com.lebocoin.ui.R

@Composable
fun ErrorComponent(
    errorType: ErrorType? = null,
    onDismiss: () -> Unit
) {
    val message = when (errorType) {
        ErrorType.ConnectivityError -> stringResource(R.string.msg_no_connection)
        ErrorType.UnknownError,
        ErrorType.ServiceError,
        ErrorType.ServerError -> stringResource(R.string.msg_unexpected_error)
        ErrorType.SecurityError -> stringResource(R.string.msg_user_is_not_authorized)
        ErrorType.EmptyError -> stringResource(R.string.msg_no_data_available)
        null -> null
    }
    AnimatedVisibility(errorType != null) {
        Box(
            modifier = Modifier
                .background(color = Color.Transparent)
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(color = MaterialTheme.colorScheme.error)
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = message ?: "",
                    color = MaterialTheme.colorScheme.onError
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = onDismiss
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_icon_close),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onError
                    )
                }
            }
        }

    }

}

@Preview
@Composable
fun ErrorComponentPreview() {
    ErrorComponent(errorType = ErrorType.EmptyError) {

    }
}