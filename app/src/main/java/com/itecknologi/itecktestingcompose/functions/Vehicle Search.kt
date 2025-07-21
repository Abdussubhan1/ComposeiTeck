package com.itecknologi.itecktestingcompose.functions

import com.itecknologi.itecktestingcompose.modelClasses.VehData
import com.itecknologi.itecktestingcompose.objects.vehicle_details

fun searchInMemory(keyword: String): List<VehData> {
    return vehicle_details.dataList.filter { data ->
        data.CHASIS.contains(keyword, ignoreCase = true) ||
                data.ENGINE.contains(keyword, ignoreCase = true)
    } //this will match the entered keyword and provide results accordingly in List form
}