package com.itecknologi.itecktestingcompose.modelClasses


data class TrackerLocationItem(
    val TLocId: Int,
    val TLocName: String
)

class TrackerLocation : ArrayList<TrackerLocationItem>()