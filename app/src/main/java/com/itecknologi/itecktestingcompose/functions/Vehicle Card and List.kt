package com.itecknologi.itecktestingcompose.functions

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.drawable.Icon
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessibilityNew
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itecknologi.itecktestingcompose.R
import com.itecknologi.itecktestingcompose.constants.Constants
import androidx.core.net.toUri
import com.itecknologi.itecktestingcompose.appPrefs.PreferenceManager
import com.itecknologi.itecktestingcompose.modelClasses.Data


enum class TaskStatus {
    PENDING,
    ACCEPTED,
    HELD,
    REJECTED
}

@Composable
fun VehicleListScreen(
    vehicleList: List<Data>,
    onSelectionChanged: (Boolean, String?, String?, String?, String?, String?, String?, String?, String?, Double?, Double?, String?) -> Unit,
    prefs: PreferenceManager
) {

    var vehicleStatuses by remember {
        mutableStateOf(prefs.getAllVehicleStatuses())
    }
    var selectedVehicle by remember { mutableStateOf<Data?>(null) }

    //Filtering the list due to rejected tasks
    val filteredVehicleList = vehicleList.filter { vehicle ->
        vehicleStatuses[vehicle.V_ID] != TaskStatus.REJECTED
    }

    LazyColumn /*(modifier = Modifier.fillMaxSize())*/ {

        items(filteredVehicleList) { vehicle ->
            val currentStatus = vehicleStatuses[vehicle.V_ID] ?: TaskStatus.PENDING
            VehicleCard(
                vehicle = vehicle,
                status = currentStatus,
                isSelected = vehicle == selectedVehicle,
                cardSelection = {
                    if (currentStatus == TaskStatus.PENDING ||
                        currentStatus == TaskStatus.HELD ||
                        currentStatus == TaskStatus.ACCEPTED
                    ) {
                        val isSame =
                            vehicle == selectedVehicle // isSame is true if the same vehicle is selected
                        selectedVehicle = if (isSame) null else vehicle //toggle selection
                        onSelectionChanged(
                            !isSame,
                            if (!isSame) vehicle.V_ID else null,
                            if (!isSame) vehicle.ENGINE else null,
                            if (!isSame) vehicle.CHASIS else null,
                            if (!isSame) vehicle.MK_NAME else null,
                            if (!isSame) vehicle.M_NAME else null,
                            if (!isSame) vehicle.Job_assigned_date else null,
                            if (!isSame) vehicle.VEH_REG else null,
                            if (!isSame) vehicle.location else null,
                            if (!isSame) vehicle.X else null,
                            if (!isSame) vehicle.Y else null,
                            if (!isSame) vehicle.Technical_job_assign_id else null
                        ) //Passes All the vehicle.Details for the card if selected, or null if deselected.

                    }

                }, onStatusChange = { newStatus ->
                    vehicleStatuses = vehicleStatuses + (vehicle.V_ID to newStatus)
                    prefs.saveVehicleStatus(vehicle.V_ID, newStatus)
                },prefs
            )
        }
    }
}

@Composable
fun VehicleCard(
    vehicle: Data,
    status: TaskStatus,
    isSelected: Boolean,
    cardSelection: () -> Unit,
    onStatusChange: (TaskStatus) -> Unit,
    prefs: PreferenceManager
) {
    val backgroundColor = if (isSelected) Color(0xFF90A4AE) else Color(0xFF102027)
    val context = LocalContext.current
    var acceptAlertDialog by remember { mutableStateOf(false) }
    var holdAlertDialog by remember { mutableStateOf(false) }
    var rejectAlertDialog by remember { mutableStateOf(false) }
    var holdReasonDialog by remember { mutableStateOf(false) }
    var holdReason by remember { mutableStateOf("") }

    val hideTheOptions = status == TaskStatus.ACCEPTED || status == TaskStatus.HELD

    val currentHoldReason = remember(vehicle.V_ID, status) {
        if (status == TaskStatus.HELD) prefs.getHoldReason(vehicle.V_ID) else null
    }

    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable { if (hideTheOptions) cardSelection() },
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFF90A4AE)),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${vehicle.MK_NAME} ${vehicle.M_NAME}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier.wrapContentSize(),
                    textAlign = TextAlign.Start
                )
                if (status != TaskStatus.PENDING) {
                    when (status){
                        TaskStatus.ACCEPTED -> Icons.Default.DoneAll
                        TaskStatus.HELD -> Icons.Outlined.CalendarMonth
                        else -> null
                    }?.let {
                        Icon(
                            imageVector = it,
                            contentDescription = "",
                            tint = when(status){
                                TaskStatus.ACCEPTED -> Color.Green
                                TaskStatus.HELD -> Color.Yellow
                                else -> Color.White
                            },
                            modifier = Modifier
                                .size(20.dp)
                        )
                    }

/*                    Text(
                        text = when (status) {
                            TaskStatus.ACCEPTED -> "ACCEPTED"
                            TaskStatus.HELD -> "ON HOLD"
                            else -> ""
                        },
                        color = when (status) {
                            TaskStatus.ACCEPTED -> Color.Green
                            TaskStatus.HELD -> Color.Yellow
                            else -> Color.White
                        },
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )*/
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Assigned Date
            Text(
                text = "Job Assigned On: ${vehicle.Job_assigned_date}",
                color = Color.White,
                fontSize = 14.sp
            )

            // Show hold reason if exists
            if (status == TaskStatus.HELD && !currentHoldReason.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)),
                    border = BorderStroke(1.dp, Color.Yellow.copy(alpha = 0.3f))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "Hold Reason:",
                            color = Color.Yellow,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = currentHoldReason,
                            color = Color.White,
                            fontSize = 13.sp,
                            lineHeight = 16.sp
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.height(10.dp))

            // Engine and Chassis
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(text = "VRN: ${vehicle.VEH_REG}", color = Color.White, fontSize = 14.sp)
                Text(
                    text = "Engine No: ${vehicle.ENGINE}",
                    color = Color.White,
                    fontSize = 14.sp
                )
                Text(
                    text = "Chassis No: ${vehicle.CHASIS}",
                    color = Color.White,
                    fontSize = 14.sp
                )
                Text(
                    text = "Location: ${vehicle.location}",
                    color = Color.White,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // View on Map Button
            Row(verticalAlignment = Alignment.CenterVertically) {
                Row(
                    modifier = Modifier
                        .clickable {
                            val sourceLat = Constants.mobileLocationLat
                            val sourceLong = Constants.mobileLocationLong
                            val destLat = vehicle.X
                            val destLong = vehicle.Y

                            val uri =
                                "https://www.google.com/maps/dir/?api=1&origin=$sourceLat,$sourceLong&destination=$destLat,$destLong&travelmode=driving".toUri()
                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            intent.setPackage("com.google.android.apps.maps")

                            try {
                                context.startActivity(intent)
                            } catch (e: ActivityNotFoundException) {
                                Toast.makeText(
                                    context,
                                    "Google Maps is not installed",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.viewmap),
                        contentDescription = "Map Icon",
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(14.dp))
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .clickable {
                            // Intent to open dialer
                            val intent = Intent(Intent.ACTION_DIAL).apply {
                                data = "tel: 03000564639".toUri()
                            }
                            context.startActivity(intent)
                        }, contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.phonecall),
                        contentDescription = "Map Icon",
                        modifier = Modifier.size(20.dp), colorFilter = ColorFilter.tint(Color.White)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = if (!hideTheOptions) Arrangement.Absolute.Right else Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    if (status == TaskStatus.PENDING) {
                        // Button 1 for Reject
                        Row(
                            modifier = Modifier
                                .clickable { rejectAlertDialog = true }
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "Reject",
                                color = Color.Red,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                textDecoration = TextDecoration.Underline,
                                fontSize = 13.sp
                            )
                        }

                        // Button 2 for Hold
                        Row(
                            modifier = Modifier
                                .clickable { holdAlertDialog = true }
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "Hold",
                                color = Color.Yellow,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                textDecoration = TextDecoration.Underline,
                                fontSize = 13.sp
                            )
                        }

                        // Button 3 for Accept
                        Row(
                            modifier = Modifier
                                .clickable { acceptAlertDialog = true }
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "Accept",
                                color = Color.Green,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                textDecoration = TextDecoration.Underline,
                                fontSize = 13.sp
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .size(16.dp)
                                .background(
                                    color = if (isSelected) Color(0xFF39B54A) else Color.Transparent,
                                    shape = CircleShape
                                )
                                .border(
                                    width = 2.dp,
                                    color = if (isSelected) Color.White else Color.Gray,
                                    shape = CircleShape
                                )
                        )
                    }

                }

            }


        }
        if (acceptAlertDialog) {
            AlertDialog(
                onDismissRequest = { acceptAlertDialog = false },
                title = { Text("Accept Task") },
                text = { Text("Are you sure you want to Accept this Task?") },
                confirmButton = {
                    TextButton(onClick = {
                        onStatusChange(TaskStatus.ACCEPTED)
                        acceptAlertDialog = false
                        Toast.makeText(context, "Task Accepted", Toast.LENGTH_SHORT).show()
                    }) {
                        Text("Accept")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { acceptAlertDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }

        if (holdAlertDialog) {
            AlertDialog(
                onDismissRequest = { holdAlertDialog = false },
                title = { Text("Hold Task") },
                text = { Text("Are you sure you want to Hold this Task?") },
                confirmButton = {
                    TextButton(onClick = {
                        /*onStatusChange(TaskStatus.HELD)*/
                        holdReasonDialog = true
                        holdAlertDialog = false
                        //Toast.makeText(context, "Task Put on Hold", Toast.LENGTH_SHORT).show()
                    }) {
                        Text("Hold")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { holdAlertDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }

        // Hold Reason Dialog
        if (holdReasonDialog) {
            AlertDialog(
                onDismissRequest = {
                    holdReasonDialog = false
                    holdReason = ""
                },
                title = { Text("Reason for Hold") },
                text = {
                    Column {
                        Text(
                            text = "Please provide a reason for holding this task:",
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        OutlinedTextField(
                            value = holdReason,
                            onValueChange = { holdReason = it },
                            label = { Text("Hold Reason") },
                            placeholder = { Text("Enter reason...") },
                            modifier = Modifier.fillMaxWidth(),
                            maxLines = 3
                        )
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            if (holdReason.trim().isNotBlank()) {
                                onStatusChange(TaskStatus.HELD)
                                prefs.saveHoldReason(vehicle.V_ID, holdReason.trim())
                                holdReasonDialog = false
                                holdReason = ""
                                Toast.makeText(context, "Task Put on Hold", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "Please enter a reason", Toast.LENGTH_SHORT).show()
                            }
                        }
                    ) {
                        Text("Submit")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        holdReasonDialog = false
                        holdReason = ""
                    }) {
                        Text("Cancel")
                    }
                }
            )
        }
        if (rejectAlertDialog) {
            AlertDialog(
                onDismissRequest = { rejectAlertDialog = false },
                title = { Text("Reject Task") },
                text = { Text("Are you sure you want to Reject this Task?") },
                confirmButton = {
                    TextButton(onClick = {
                        onStatusChange(TaskStatus.REJECTED)
                        rejectAlertDialog = false
                        Toast.makeText(context, "Task Rejected!", Toast.LENGTH_SHORT).show()
                    }) {
                        Text("Reject")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { rejectAlertDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

@Composable
fun SelectedVehicle() {
    val backgroundColor = Color(0xFF102027)
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFF90A4AE)),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Vehicle info
            Text(
                text = "${Constants.make} ${Constants.model}",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Assigned Date
            Text(
                text = "Job Assigned On: ${Constants.JobAssigneddate}",
                color = Color.White,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Engine and Chassis
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(text = "VRN: ${Constants.VRN}", color = Color.White, fontSize = 14.sp)
                Text(
                    text = "Engine No: ${Constants.engineNumber}",
                    color = Color.White,
                    fontSize = 14.sp
                )
                Text(
                    text = "Chassis No: ${Constants.chassisNumber}",
                    color = Color.White,
                    fontSize = 14.sp
                )
                Text(
                    text = "Location: ${Constants.jobAssignedLoc}",
                    color = Color.White,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF39B54A))
                    .clickable {

                        val sourceLat = Constants.mobileLocationLat
                        val sourceLong = Constants.mobileLocationLong
                        val destLat = Constants.X
                        val destLong = Constants.Y

                        val uri =
                            "https://www.google.com/maps/dir/?api=1&origin=$sourceLat,$sourceLong&destination=$destLat,$destLong&travelmode=driving".toUri()
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        intent.setPackage("com.google.android.apps.maps")

                        try {
                            context.startActivity(intent)
                        } catch (e: ActivityNotFoundException) {
                            Toast.makeText(
                                context,
                                "Google Maps is not installed",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Get Directions",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    textDecoration = TextDecoration.Underline
                )
                Spacer(modifier = Modifier.width(6.dp))
                Image(
                    painter = painterResource(id = R.drawable.viewmap),
                    contentDescription = "Map Icon",
                    modifier = Modifier.size(20.dp)
                )
            }

        }
    }
}


@Preview
@Composable
fun VehicleCardInList() {
    VehicleCard(
        vehicle = Data(
            "",
            ENGINE = "",
            Job_assigned_date = "",
            Job_completed_date = "",
            MK_NAME = "",
            M_NAME = "",
            T_NAME = "",
            Technical_job_assign_id = "",
            VEH_REG = "",
            V_ID = "",
            X = 0.0,
            Y = 0.0,
            Y_NAME = "",
            location = "",
            status = "",
            type = "",
        ),
        status = TaskStatus.PENDING,
        isSelected = false,
        cardSelection = {},
        onStatusChange = {},
        prefs = PreferenceManager(LocalContext.current)
    )
}

/*
@Preview
@Composable
fun selected(){
    SelectedVehicle()

}*/
