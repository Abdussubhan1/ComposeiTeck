package com.itecknologi.itecktestingcompose.functions

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itecknologi.itecktestingcompose.modelClasses.VehData

@Composable
fun VehicleCard(
    vehicle: VehData,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) Color(0xFF90A4AE) else Color(0xFF102027)
    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFF90A4AE)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "${vehicle.MAKE} ${vehicle.MODEL}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Color: ${vehicle.COLOR}", color = Color.White)
                Text(text = "Engine: ${vehicle.ENGINE}", color = Color.White)
                Text(text = "Chassis: ${vehicle.CHASSIS}", color = Color.White)
            }
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
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

@Composable
fun VehicleListScreen(
    vehicleList: List<VehData>,
    onSelectionChanged: (Boolean, String?) -> Unit
) {
    var selectedVehicle by remember { mutableStateOf<VehData?>(null) }

    LazyColumn {
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
                        if (!isSame) vehicle.V_ID else null
                    ) //Passes vehicle.V_ID if selected, or null if deselected.
                }
            )
        }
    }
}