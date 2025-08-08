package com.itecknologi.itecktestingcompose.modelClasses

data class vehicleDetailsCheck(
    val Success: Boolean,
    val `data`: List<Data>
)

data class Data(
    val CHASIS: String,
    val ENGINE: String,
    val Job_assigned_date: String,
    val Job_completed_date: Any,
    val MK_NAME: String,
    val M_NAME: String,
    val T_NAME: String,
    val Technical_job_assign_id: String,
    val VEH_REG: String,
    val V_ID: String,
    val X: Double,
    val Y: Double,
    val Y_NAME: String,
    val status: String,
    val type: String,
    val poc_location:String,
    val customer_name:String,
    val customer_number: String,
    val comments: String?,
    val status_id: Int,
    val poc_number_id:String
)