package com.itecknologi.itecktestingcompose.modelClasses

data class GetLocation(
    var Lat: Double,
    var Lng: Double,
    val Message: String,
    val Success: Boolean
)