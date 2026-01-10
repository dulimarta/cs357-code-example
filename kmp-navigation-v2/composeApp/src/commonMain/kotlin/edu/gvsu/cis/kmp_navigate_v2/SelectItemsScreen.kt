package edu.gvsu.cis.kmp_navigate_v2

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kmpnavigationv2.composeapp.generated.resources.Res
import kmpnavigationv2.composeapp.generated.resources.minus
import kmpnavigationv2.composeapp.generated.resources.plus
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SelectItemsScreen(
    modifier: Modifier = Modifier,
    vm: AppViewModel, onSelectionComplete: () -> Unit
) {
    val products by vm.availableItems.collectAsState()
    val totalPrice by vm.totalPrice.collectAsState()
    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = onSelectionComplete,
                enabled = totalPrice > 0
            ) {
                Text("Confirm Orders")
            }
            Text("Total ${totalPrice.toCurrency()}", fontSize = 20.sp)
        }
        LazyColumn {
            items(products) {
                var quantity by remember { mutableStateOf(vm.getOrderQuantity(it.id)) }
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.border(width = 1.dp, color = Color.Black).fillMaxWidth()
                        .padding(4.dp)
                        .background(if (quantity > 0) Color.LightGray else Color.Transparent)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(it.description, fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp)
                        Text("${it.price.toCurrency()}")
                    }

                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        modifier = Modifier.border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(percent = 50)
                        ).padding(0.dp),
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            if (quantity > 0) {
                                quantity--
                                vm.setOrderQuantity(it.id, quantity)
                            }
                        }) {
                            Icon(
                                modifier = Modifier.scale(2.5f),
                                painter = painterResource(Res.drawable.minus),
                                contentDescription = null
                            )
                        }
                        Text("$quantity")
                        IconButton(onClick = {
                            quantity++
                            vm.setOrderQuantity(it.id, quantity)
                        }) {
                            Icon(
                                modifier = Modifier.scale(2.5f),
                                painter = painterResource(Res.drawable.plus),
                                contentDescription = null
                            )
                        }
                    }
//                    IconButton(onClick = { /*TODO*/ }) {
//                        Icon(painter = painterResource(Res.drawable.plus1), contentDescription = null)
//                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectItemsPreview() {
    SelectItemsScreen(vm = viewModel(), onSelectionComplete = {})
}