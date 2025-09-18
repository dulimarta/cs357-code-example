package edu.gvsu.cis.composenavigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {

    @Serializable
    data object SelectItems
    @Serializable
    data object ShoppingCart
    @Serializable
    data object SelectShippingAddress
    @Serializable
    data class SelectPayment(val totalCharge: Float)
    @Serializable
    data class Checkout(val totalCharge: Float)
}