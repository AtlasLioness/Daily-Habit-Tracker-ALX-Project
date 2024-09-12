package com.example.dailyhabittracker.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Parcelize
@Entity(tableName = "habit_table")
data class Habit (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val habit_title: String,
    val habit_description: String,
    val habit_startTime: String,
    var isChecked: Boolean = false,
    var lastCheckedDate: String? = null // Track the last date checked
): Parcelable {
    fun updateCheckedState(checked: Boolean) {
        if (canCheckHabitToday()) {
            isChecked = checked
            lastCheckedDate = getCurrentDate()
        }
    }

    private fun canCheckHabitToday(): Boolean {
        val currentDate = getCurrentDate()
        return lastCheckedDate == null || lastCheckedDate != currentDate
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Date())
    }
}
