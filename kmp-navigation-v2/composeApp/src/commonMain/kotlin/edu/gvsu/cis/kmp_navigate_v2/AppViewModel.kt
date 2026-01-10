package edu.gvsu.cis.kmp_navigate_v2

import androidx.lifecycle.ViewModel
import io.spherelabs.blahblahfake.internal.blah
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class Item(val id: String = "", val description: String = "", val price: Float = 0f)
data class OrderedItem(val description: String = "", val quantity: Int = 0)

class AppViewModel : ViewModel() {

    val faker = blah()

    @OptIn(ExperimentalUuidApi::class)
    val availableItems = MutableStateFlow(List(40) {
        Item(
            id = Uuid.random().toString(),
            description = faker.commerce.product.adjective.toString() + " " + faker.commerce.product.material.toString(),
            price = (0..100).random().toFloat()
        )
    }).asStateFlow()
    private val orderMap = mutableMapOf<String, Int>()
    private val _totalPrice = MutableStateFlow(0f)
    val totalPrice = _totalPrice.asStateFlow()

    private val _orderList: MutableStateFlow<List<OrderedItem>> = MutableStateFlow(emptyList())

    val orderList = _orderList.asStateFlow()

    fun setOrderQuantity(id: String, quantity: Int) {
        if (quantity == 0) {
            orderMap.remove(id)
        } else {
            orderMap[id] = quantity
        }
        orderMap.forEach { (id, quantity) ->
            val item = availableItems.value.find { it.id == id }
            if (item != null) {
                _totalPrice.value += item.price * quantity
            }
        }
        _orderList.update {
            orderMap.map { item ->
                val z = availableItems.value.find { it.id == item.key }
                OrderedItem(description = z?.description ?: "", quantity = item.value)
            }
        }

    }

    fun getOrderQuantity(id: String): Int {
        return orderMap[id] ?: 0
    }

    fun resetOrder() {
        _orderList.value = emptyList()
        _totalPrice.value = 0f
        orderMap.clear()
    }
}