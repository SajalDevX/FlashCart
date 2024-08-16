package me.mrsajal.flashcart.android.presentation.users.customer.cart.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import me.mrsajal.flashcart.features.profile.data.UserAddress
@Composable
fun AddressBottomSheetContent(
    modifier: Modifier = Modifier,
    onDismissModalSheet: () -> Unit,
    addresses: List<UserAddress>,
    selectedAddress: UserAddress?,
    onSelectAddress: (UserAddress) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight() // Ensures the column takes up the available height
    ) {
        Box(
            modifier = modifier
                .height(56.dp)
                .border(1.dp, Color.Gray)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth()
            ) {
                IconButton(
                    onClick = { onDismissModalSheet() },
                    modifier = modifier
                        .padding(end = 8.dp)
                        .aspectRatio(1.0f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close"
                    )
                }
                Text(
                    "Select Delivery Address",
                    fontWeight = FontWeight(600),
                    modifier = modifier.weight(1.0f)
                )
            }
        }
        // Remove the aspectRatio constraint to allow LazyColumn to expand and scroll
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .weight(1.0f) // Takes up the remaining space
        ) {
            items(addresses) { address ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickable { onSelectAddress(address) }
                ) {
                    RadioButton(
                        selected = address == selectedAddress,
                        onClick = { onSelectAddress(address) }
                    )
                    Column(
                        modifier = modifier
                            .padding(start = 8.dp)
                    ) {
                        Text(
                            text = "${address.fatherName}, ${address.pin}",
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${address.road}, ${address.city}",
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}
