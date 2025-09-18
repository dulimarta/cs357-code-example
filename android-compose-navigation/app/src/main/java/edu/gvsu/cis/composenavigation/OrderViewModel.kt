package edu.gvsu.cis.composenavigation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class OrderViewModel: ViewModel() {

    private val _orders = MutableStateFlow(listOf<String>())
    val orders = _orders.asStateFlow()

    fun addOrders(items: List<String>) {
        _orders.value = emptyList()
        items.forEach {
            _orders.value = _orders.value + it
        }
    }
    fun removeOrder(item: String) {
        _orders.value = _orders.value.filter { it != item }
    }
}