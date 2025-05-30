package com.example.itecktestingcompose.objects

import com.example.itecktestingcompose.modelClasses.TrackerLocationItem
import com.example.itecktestingcompose.modelClasses.VehData


object vehicle_details {
    var dataList: List<VehData> = emptyList()
}
object deviceInstallationPlaces {
    var places = ArrayList<TrackerLocationItem>()
}