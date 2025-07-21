package com.itecknologi.itecktestingcompose.modelClasses

data class VehicleDetails(
    val data: List<VehData>,
    val success: Boolean,
    val message: String
)
data class VehData(
    val CHASIS: String,
    val ENGINE: String,
    val V_ID:String,
    val MK_NAME:String,
    val M_NAME:String,
    val Job_assigned_date: String,
    val Job_completed_date: Any,
    val T_NAME: String,
    val VEH_REG: String,
    val X: Double,
    val Y: Double,
    val status: String,
    val type: String,
    val location:String
)