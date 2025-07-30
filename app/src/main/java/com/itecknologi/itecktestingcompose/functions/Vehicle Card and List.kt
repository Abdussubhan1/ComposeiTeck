package com.itecknologi.itecktestingcompose.functions

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
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
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.itecknologi.itecktestingcompose.modelClasses.Data

@Composable
fun VehicleCard(
    vehicle: Data,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) Color(0xFF90A4AE) else Color(0xFF102027)
    val context = LocalContext.current
    var hideTheOptions by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable { if (hideTheOptions) onClick() },
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
                text = "${vehicle.MK_NAME} ${vehicle.M_NAME}",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Assigned Date
            Text(
                text = "Job Assigned On: ${vehicle.Job_assigned_date}",
                color = Color.White,
                fontSize = 14.sp
            )

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
                        modifier = Modifier.size(28.dp)
                    )
                }
                Spacer(modifier = Modifier.width(14.dp))
                Box(modifier = Modifier.wrapContentSize().clickable {                     // Intent to open dialer
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = "tel: 03000564639".toUri()
                    }
                    context.startActivity(intent) },contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(id = R.drawable.phonecall),
                        contentDescription = "Map Icon",
                        modifier = Modifier.size(28.dp), colorFilter = ColorFilter.tint(Color.White)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = if (!hideTheOptions) Arrangement.Absolute.Right else Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!hideTheOptions) {

                        //Button 1 for Reject
                        Row(
                            modifier = Modifier
                                .clickable {
                                    //todo: reject Click handling
                                }
                                .padding(8.dp)
                        ) {

                            Text(
                                text = "Reject",
                                color = Color.Red,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                textDecoration = TextDecoration.Underline, fontSize = 13.sp
                            )

                        }

                        //Button 2 for Hold
                        Row(
                            modifier = Modifier
                                .clickable {
                                    //todo
                                }
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "Hold",
                                color = Color.Yellow,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                textDecoration = TextDecoration.Underline, fontSize = 13.sp
                            )
                        }

                        //Button 3 for Accept
                        Row(
                            modifier = Modifier
                                .clickable {
                                    hideTheOptions = true
                                }
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "Accept",
                                color = Color.Green,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                textDecoration = TextDecoration.Underline, fontSize = 13.sp
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
    }
}


@Composable
fun VehicleListScreen(
    vehicleList: List<Data>,
    onSelectionChanged: (Boolean, String?, String?, String?, String?, String?, String?, String?, String?, Double?, Double?, String?) -> Unit
) {
    var selectedVehicle by remember { mutableStateOf<Data?>(null) }

    LazyColumn /*(modifier = Modifier.fillMaxSize())*/ {
        items(vehicleList) { vehicle ->
            VehicleCard(
                vehicle = vehicle,
                isSelected = vehicle == selectedVehicle,
                onClick = {
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
                textAlign = TextAlign.Center
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
fun SelectedVehiclePre() {
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
        isSelected = false,
        onClick = {}
    )
}
