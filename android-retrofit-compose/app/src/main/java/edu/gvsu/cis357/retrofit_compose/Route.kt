package edu.gvsu.cis357.retrofit_compose

import kotlinx.serialization.Serializable

@Serializable
sealed class Route {

    @Serializable
    data object RandomUser

    @Serializable
    data object Other
}