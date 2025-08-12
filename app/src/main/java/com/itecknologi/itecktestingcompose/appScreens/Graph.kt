package com.itecknologi.itecktestingcompose.appScreens

import android.graphics.drawable.GradientDrawable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter



@Composable
fun StatsLineChart(
    stats: Map<String, Int>, // Pass your dynamic map here
    modifier: Modifier = Modifier
) {
    val dayOrder = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    val sortedList = dayOrder.mapNotNull { day ->
        stats[day]?.let { value -> day to value }
    }
    val labels = sortedList.map { it.first } // Maintain order
    val entries = sortedList.mapIndexed { index, (_, value) ->
        Entry(index.toFloat(), value.toFloat())
    }

    val primary = Color(0xFF1E88E5).toArgb()   // Medium Sky Blue
    val secondary = Color(0xFF42A5F5).toArgb() // Light Blue Accent
    val onBackground = Color(0xFFFFFFFF).toArgb() // Pure White

    val gradientDrawable = GradientDrawable(
        GradientDrawable.Orientation.TOP_BOTTOM,
        intArrayOf(
            Color(0xFF81D4FA).copy(alpha = 0.5f).toArgb(), // Soft Sky Blue
            Color.White.copy(alpha = 0.1f).toArgb()        // Faint White
        )
    )

    AndroidView(
        modifier = modifier,
        factory = { context ->
            LineChart(context).apply {

                xAxis.setDrawGridLines(false)
                axisLeft.setDrawGridLines(false)
                axisLeft.axisMinimum = 0f // Prevent negative Y values
                description.isEnabled = false
                setTouchEnabled(false)

                // X Axis setup
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.valueFormatter = IndexAxisValueFormatter(labels)
                xAxis.textColor = onBackground
                xAxis.labelRotationAngle = 0f // Tilt for readability
                axisLeft.axisMinimum=0f

                // Y Axis setup
                axisLeft.textColor = onBackground
                axisLeft.granularity=1f
                axisRight.isEnabled = false

                axisLeft.valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return value.toInt().toString()
                    }
                }

                // Legend
                legend.textColor = onBackground
                legend.textSize=14f
                legend.verticalAlignment = com.github.mikephil.charting.components.Legend.LegendVerticalAlignment.TOP

                extraTopOffset=20f

                // DataSet
                val dataSet = LineDataSet(entries, "Last 7 Days Of Activities").apply {
                    color = primary
                    setCircleColor(secondary)
                    lineWidth = 2f
                    circleRadius = 3f
                    setDrawValues(true)
                    valueTextColor = onBackground
                    valueTextSize = 13f
                    setDrawFilled(true)
                    fillDrawable = gradientDrawable

                    // Format point labels as integers
                    valueFormatter = object : ValueFormatter() {
                        override fun getFormattedValue(value: Float): String {
                            return value.toInt().toString()
                        }
                    }
                }

                data = LineData(dataSet)
                invalidate()
            }
        }
    )
}

@Preview
@Composable
fun StatsLineChartPreview() {
    val sampleData = mapOf(
        "Sun" to 10,
        "Mon" to 20,
        "Tue" to 165,
        "Wed" to 125,
        "Thu" to 55,
        "Fri" to 35,
        "Sat" to 20

    )

    StatsLineChart(
        stats = sampleData,
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    )
}