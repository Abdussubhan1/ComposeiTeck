package com.example.itecktestingcompose.ModelClasses

data class VehicleDetails(
    val Data: List<VehData>,
    val Message: String,
    val Success: Boolean
)

data class VehData(
    val CHASSIS: String,
    val COLOR: String,
    val ENGINE: String,
    val MAKE: String,
    val MODEL: String,
    val V_ID: String
)