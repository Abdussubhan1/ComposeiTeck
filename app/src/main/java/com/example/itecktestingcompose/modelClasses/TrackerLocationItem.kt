package com.example.itecktestingcompose.modelClasses


data class TrackerLocationItem(
    val TLocId: Int,
    val TLocName: String
)

class TrackerLocation : ArrayList<TrackerLocationItem>()