package com.example.waffle.composables


import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.waffle.viewmodel.WaffleViewModel
import com.solana.mobilewalletadapter.clientlib.ActivityResultSender


@Composable
fun WalletConnectButton(
    identityUri: Uri,
    iconUri: Uri,
    identityName: String,
    activityResultSender: ActivityResultSender,
    modifier: Modifier,
    waffleViewModel: WaffleViewModel = hiltViewModel()
) {
    val viewState = waffleViewModel.viewState.collectAsState().value
    val pubKey = viewState.userAddress
    val buttonText = when {
        viewState.noWallet -> "Please install a wallet"
        pubKey.isEmpty() -> "Connect Wallet"
        viewState.userAddress.isNotEmpty() -> pubKey.take(4).plus("...").plus(pubKey.takeLast(4))
        else -> ""
    }
    Column(modifier = modifier) {
        Button(
            modifier = modifier,
            onClick = {
                if (viewState.userAddress.isEmpty()) {
                    waffleViewModel.connect(identityUri, iconUri, identityName, activityResultSender)
                } else {
                    waffleViewModel.disconnect()
                }
            },
        ) {
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = buttonText,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
