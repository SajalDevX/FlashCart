package me.mrsajal.flashcart.android.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onNavToOrders: () -> Unit,
    onNavToWishlist: () -> Unit,
    onNavToSettings: () -> Unit,
    onNavToHelp: () -> Unit,
    onNavToAbout: () -> Unit,
    onLogout: () -> Unit,
    onNaviToAddress: () -> Unit,
    onNaviToEditProfile: () -> Unit,
    onNavToReviews: () -> Unit,
    onNavToCart: () -> Unit,
    uiState: ProfileUiState,
) {
    if (uiState.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    modifier = modifier
                        .background(Color.Transparent)
                        .border(1.dp, Color.Gray.copy(0.3f)),
                    contentColor = Color.Black,
                    backgroundColor = Color.White,
                    elevation = 0.dp,
                    title = {
                        Text(
                            text = "Hey! " + uiState.profile!!.name,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 24.sp,
                            color = Color.Black,
                            modifier = modifier.padding(horizontal = 16.dp)
                        )
                    },
                )
            },
        ) { innerPadding ->
            LazyColumn(
                modifier = modifier.padding(innerPadding)
            ) {
                item {
                    Spacer(modifier = modifier.height(12.dp))
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(8.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Row(
                            modifier = modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedButton(
                                onClick = onNavToOrders, modifier = modifier.weight(1f)
                            ) {
                                Row(
                                    modifier = modifier.fillMaxSize(),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.DateRange,
                                        contentDescription = null
                                    )
                                    Spacer(modifier = modifier.width(8.dp))
                                    Text(
                                        text = "Orders",
                                        fontSize = 14.sp,
                                        color = Color.DarkGray,
                                        fontFamily = FontFamily.SansSerif,
                                        fontWeight = FontWeight.Normal
                                    )
                                }
                            }
                            Spacer(modifier = modifier.width(4.dp))
                            OutlinedButton(
                                onClick = onNavToWishlist, modifier = modifier.weight(1f)
                            ) {
                                Row(
                                    modifier = modifier.fillMaxSize(),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.FavoriteBorder, contentDescription = null
                                    )
                                    Spacer(modifier = modifier.width(8.dp))
                                    Text(
                                        text = "Wishlist",
                                        fontSize = 14.sp,
                                        color = Color.DarkGray,
                                        fontFamily = FontFamily.SansSerif,
                                        fontWeight = FontWeight.Normal
                                    )
                                }
                            }
                        }
                        Row(
                            modifier = modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedButton(
                                onClick = onNavToCart, modifier = modifier.weight(1f)
                            ) {
                                Row(
                                    modifier = modifier.fillMaxSize(),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.ShoppingCart, contentDescription = null
                                    )
                                    Spacer(modifier = modifier.width(8.dp))
                                    Text(
                                        text = "My Cart",
                                        fontSize = 14.sp,
                                        color = Color.DarkGray,
                                        fontFamily = FontFamily.SansSerif,
                                        fontWeight = FontWeight.Normal
                                    )
                                }
                            }
                            Spacer(modifier = modifier.width(4.dp))
                            OutlinedButton(
                                onClick = onNavToHelp, modifier = modifier.weight(1f)
                            ) {
                                Row(
                                    modifier = modifier.fillMaxSize(),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.CheckCircle, contentDescription = null
                                    )
                                    Spacer(modifier = modifier.width(8.dp))
                                    Text(
                                        text = "Help Center",
                                        fontSize = 14.sp,
                                        color = Color.DarkGray,
                                        fontFamily = FontFamily.SansSerif,
                                        fontWeight = FontWeight.Normal
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = modifier.height(12.dp))
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .background(Color.White),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Account Settings",
                            fontSize = 20.sp,
                            color = Color.Black,
                            modifier = modifier
                                .padding(horizontal = 16.dp)
                                .padding(bottom = 8.dp, top = 16.dp),
                            fontWeight = FontWeight.Bold,
                        )
                        Column(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            TextButton(
                                onClick = onNavToOrders, modifier = modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.DateRange, contentDescription = null
                                    )
                                    Spacer(modifier = modifier.width(8.dp))
                                    Text(
                                        text = "Flashcart Plus",
                                        fontSize = 14.sp,
                                        color = Color.DarkGray,
                                        fontFamily = FontFamily.SansSerif,
                                        fontWeight = FontWeight.Normal
                                    )
                                }
                            }
                            Spacer(modifier = modifier.height(8.dp))
                            TextButton(
                                onClick = onNaviToEditProfile, modifier = modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.DateRange, contentDescription = null
                                    )
                                    Spacer(modifier = modifier.width(8.dp))
                                    Text(
                                        text = "Edit Profile",
                                        fontSize = 14.sp,
                                        color = Color.DarkGray,
                                        fontFamily = FontFamily.SansSerif,
                                        fontWeight = FontWeight.Normal
                                    )
                                }
                            }
                            Spacer(modifier = modifier.height(8.dp))
                            TextButton(
                                onClick = onNaviToAddress, modifier = modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.DateRange, contentDescription = null
                                    )
                                    Spacer(modifier = modifier.width(8.dp))
                                    Text(
                                        text = "Saved Addresses",
                                        fontSize = 14.sp,
                                        color = Color.DarkGray,
                                        fontFamily = FontFamily.SansSerif,
                                        fontWeight = FontWeight.Normal
                                    )
                                }
                            }
                            Spacer(modifier = modifier.height(8.dp))
                            TextButton(
                                onClick = onNavToReviews, modifier = modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.DateRange, contentDescription = null
                                    )
                                    Spacer(modifier = modifier.width(8.dp))
                                    Text(
                                        text = "Notification Settings",
                                        fontSize = 14.sp,
                                        color = Color.DarkGray,
                                        fontFamily = FontFamily.SansSerif,
                                        fontWeight = FontWeight.Normal
                                    )
                                }
                            }
                            Spacer(modifier = modifier.height(8.dp))
                            TextButton(
                                onClick = onNavToSettings, modifier = modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.DateRange, contentDescription = null
                                    )
                                    Spacer(modifier = modifier.width(8.dp))
                                    Text(
                                        text = "Advanced Settings",
                                        fontSize = 14.sp,
                                        color = Color.DarkGray,
                                        fontFamily = FontFamily.SansSerif,
                                        fontWeight = FontWeight.Normal
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = modifier.height(12.dp))
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .background(Color.White),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "My Activity",
                            fontSize = 20.sp,
                            color = Color.Black,
                            modifier = modifier
                                .padding(horizontal = 16.dp)
                                .padding(bottom = 8.dp, top = 16.dp),
                            fontWeight = FontWeight.Bold,
                        )
                        Column(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                                .background(Color.White), horizontalAlignment = Alignment.Start
                        ) {
                            TextButton(
                                onClick = onNavToSettings, modifier = modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.DateRange, contentDescription = null
                                    )
                                    Spacer(modifier = modifier.width(8.dp))
                                    Text(
                                        text = "Reviews",
                                        fontSize = 14.sp,
                                        color = Color.DarkGray,
                                        fontFamily = FontFamily.SansSerif,
                                        fontWeight = FontWeight.Normal
                                    )
                                }
                            }
                            TextButton(
                                onClick = onNavToSettings, modifier = modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.DateRange, contentDescription = null
                                    )
                                    Spacer(modifier = modifier.width(8.dp))
                                    Text(
                                        text = "Questions and Answer",
                                        fontSize = 14.sp,
                                        color = Color.DarkGray,
                                        fontFamily = FontFamily.SansSerif,
                                        fontWeight = FontWeight.Normal
                                    )
                                }
                            }
                            if (uiState.profile?.userDetails!!.userRole == "customer") {
                                TextButton(
                                    onClick = onNavToSettings, modifier = modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            Icons.Default.DateRange, contentDescription = null
                                        )
                                        Spacer(modifier = modifier.width(8.dp))
                                        Text(
                                            text = "Sell on Flashcart",
                                            fontSize = 14.sp,
                                            color = Color.DarkGray,
                                            fontFamily = FontFamily.SansSerif,
                                            fontWeight = FontWeight.Normal
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = modifier.height(12.dp))

                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .background(Color.White),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        TextButton(
                            onClick = onNavToAbout, modifier = modifier.fillMaxWidth(),
                        ) {
                            Text(
                                text = "About",
                                fontSize = 16.sp,
                                color = Color.Blue,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Divider(modifier = modifier.height(8.dp).background(Color.Gray.copy(0.005f)))
                        TextButton(
                            onClick = onLogout, modifier = modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Logout",
                                fontSize = 16.sp,
                                color = Color.Blue,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}