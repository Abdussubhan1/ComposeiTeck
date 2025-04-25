package com.example.itecktestingcompose.ModelClasses

data class ValidateCnicResponse(
    val Message: String,
    val Name: String,
    val Success: Boolean,
    val Id: Int,
    val AppLoginid:String

)

//{
//    "Success": true,
//    "Message": "Valid CNIC, new record inserted.",
//    "Name": "ABDUS SUBHAN",
//    "Id": 129,
//    "AppLoginid": "2"
//}