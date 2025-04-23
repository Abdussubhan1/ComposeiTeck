package com.example.itecktestingcompose.ModelClasses

data class Status(
    val GPSTime: String,
    val GSMSignal: Int,
    val Ignition: String,
    val Location: String,
    val PowerVoltages: String
)