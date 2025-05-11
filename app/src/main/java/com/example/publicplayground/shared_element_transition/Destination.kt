package com.example.publicplayground.shared_element_transition

import kotlinx.serialization.Serializable

@Serializable
sealed class Destination {
    @Serializable
    object BirdGrid : Destination()

    @Serializable
    data class BirdDetail(val birdId: Int) : Destination()

    @Serializable
    object CameraSpec : Destination()

    @Serializable
    object Quiz : Destination()
}
