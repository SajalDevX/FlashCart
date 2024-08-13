package me.mrsajal.flashcart.android.presentation.users.customer.address

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import me.mrsajal.flashcart.features.profile.data.UserAddress

@Composable
fun AddressComponent(
    modifier: Modifier = Modifier,
    uiState: AddressUiState,
    onEvent: (AddressEvent) -> Unit,
    onNavigateToAddAddress: () -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.LightGray.copy(alpha = 0.05f)),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        item {
            TextButton(
                onClick = { onNavigateToAddAddress() },
                modifier = modifier
                    .fillMaxWidth().height(64.dp)
                    .background(Color.White),
            ) {
                Text(text = "Add a new address", style = MaterialTheme.typography.h5)
            }
            Spacer(modifier = modifier.height(4.dp))
        }
        val addresses = uiState.addresses ?: emptyList()
        items(addresses) { address ->
            val index = addresses.indexOf(address)
            AddressCard(
                modifier = modifier
                    .background(Color.White)
                    .padding(8.dp),
                userAddress = address,
                onDelete = { onEvent(AddressEvent.DeleteAddress(index)) }
            )
            Spacer(modifier = modifier.height(4.dp))
        }
    }
}

@Composable
fun AddressCard(
    modifier: Modifier = Modifier,
    userAddress: UserAddress,
    onDelete: () -> Unit
) {
    Row (
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Column(
            modifier = modifier
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = userAddress.fatherName,
                style = MaterialTheme.typography.h6,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${userAddress.road}, ${userAddress.city}",
                style = MaterialTheme.typography.subtitle1
            )
            Text(
                text = "${userAddress.state} - ${userAddress.pin}",
                style = MaterialTheme.typography.subtitle1
            )
            Text(
                text = userAddress.mobileNumber,
                style = MaterialTheme.typography.subtitle1
            )
        }
        TextButton(onClick = { onDelete() }) {
            Text(text = "Delete", style = MaterialTheme.typography.button)
        }
    }
}
