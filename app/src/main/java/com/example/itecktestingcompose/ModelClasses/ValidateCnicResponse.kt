package com.example.itecktestingcompose.ModelClasses

data class ValidateCnicResponse(
    val Message: String,
    val Name: String,
    val Success: Boolean,
    val Id: Int,
    val otp:String,
    val AppLoginid:String


)
