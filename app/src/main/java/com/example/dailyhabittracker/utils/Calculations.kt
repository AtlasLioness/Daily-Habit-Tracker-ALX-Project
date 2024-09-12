package com.example.dailyhabittracker.utils

import java.sql.Timestamp
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Calculations {

    private val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    // Function to calculate time between a given startDate and the current time
    fun calculateTimeBetweenDates(startDate: String): String {
        val endDate = timeStampToString(System.currentTimeMillis())

        return try {
            val date1 = sdf.parse(startDate) ?: return "Invalid start date"
            val date2 = sdf.parse(endDate) ?: return "Invalid current date"

            var difference = date2.time - date1.time
            var isNegative = false

            if (difference < 0) {
                difference = -difference
                isNegative = true
            }

            val minutes = difference / (60 * 1000)
            val hours = difference / (60 * 60 * 1000)
            val days = difference / (24 * 60 * 60 * 1000)
            val months = days / 30 // Approximate month length
            val years = days / 365 // Approximate year length

            if (isNegative) {
                when {
                    minutes < 240 -> "Starts in $minutes minutes"
                    hours < 48 -> "Starts in $hours hours"
                    days < 61 -> "Starts in $days days"
                    months < 24 -> "Starts in $months months"
                    else -> "Starts in $years years"
                }
            } else {
                when {
                    minutes < 240 -> "$minutes minutes ago"
                    hours < 48 -> "$hours hours ago"
                    days < 61 -> "$days days ago"
                    months < 24 -> "$months months ago"
                    else -> "$years years ago"
                }
            }
        } catch (e: ParseException) {
            "Date parsing error"
        }
    }

    // Convert a timestamp to a formatted date string
    private fun timeStampToString(timeStamp: Long): String {
        val stamp = Timestamp(timeStamp)
        return sdf.format(Date(stamp.time))
    }

    // Format day, month, and year into a string
    fun cleanDate(_day: Int, _month: Int, _year: Int): String {
        val day = _day.toString().padStart(2, '0')
        val month = (_month + 1).toString().padStart(2, '0')
        return "$day/$month/$_year"
    }

    // Format hour and minute into a string
    fun cleanTime(_hour: Int, _minute: Int): String {
        val hour = _hour.toString().padStart(2, '0')
        val minute = _minute.toString().padStart(2, '0')
        return "$hour:$minute"
    }
}
