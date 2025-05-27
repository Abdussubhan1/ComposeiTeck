package com.example.itecktestingcompose.modelClasses

data class ValidateCnicResponse(
    val Message: String,
    val Name: String,
    val Success: Boolean,
    val Id: Int,
    val otp:String,
    val AppLoginid:String,
    val T_ID:Int,
    val Authkey:String


)
