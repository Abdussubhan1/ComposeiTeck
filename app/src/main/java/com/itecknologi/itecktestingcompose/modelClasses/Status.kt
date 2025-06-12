package com.itecknologi.itecktestingcompose.modelClasses

import com.google.gson.annotations.SerializedName

data class Status(
    val GPSTime: String,
    @SerializedName("GSM Signal")
    val GSMSignal: Int,
    val Ignition: String,
    val Location: String,
    @SerializedName("Power Voltages")
    val PowerVoltages: String
)
