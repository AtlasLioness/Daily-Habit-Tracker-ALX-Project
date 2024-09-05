package com.example.dailyhabittracker.ui.fragments.createhabit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dailyhabittracker.R
import com.example.dailyhabittracker.ui.viewmodels.HabitViewModel
import com.example.dailyhabittracker.utils.Calculations
import java.util.*
import android.widget.TextView
import android.widget.TimePicker

class CreateHabitItem : Fragment(R.layout.fragment_create_habit_item),
    TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private var title = ""
    private var description = ""
    private var drawableSelected = 0
    private var timeStamp = ""

    private lateinit var habitViewModel: HabitViewModel

    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minute = 0

    private var cleanDate = ""
    private var cleanTime = ""

    // Declare buttons and text views
    private lateinit var btn_confirm: Button
    private lateinit var btn_pickDate: Button
    private lateinit var btn_pickTime: Button
    private lateinit var tv_timeSelected: TextView
    private lateinit var tv_dateSelected: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        habitViewModel = ViewModelProvider(this).get(HabitViewModel::class.java)

        // Initialize buttons and text views
        btn_confirm = view.findViewById(R.id.btn_confirm)
        btn_pickDate = view.findViewById(R.id.btn_pickDate)
        btn_pickTime = view.findViewById(R.id.btn_pickTime)
        tv_timeSelected = view.findViewById(R.id.tv_timeSelected)
        tv_dateSelected = view.findViewById(R.id.tv_dateSelected)

        btn_confirm.setOnClickListener {
            // addHabitToDB()
        }

        pickDateAndTime()
    }

    private fun pickDateAndTime() {
        btn_pickDate.setOnClickListener {
            getDateCalendar()
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }

        btn_pickTime.setOnClickListener {
            getTimeCalendar()
            TimePickerDialog(context, this, hour, minute, true).show()
        }
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        cleanTime = Calculations.cleanTime(p1, p2)
        tv_timeSelected.text = "Time: $cleanTime"
    }

    override fun onDateSet(p0: DatePicker?, yearX: Int, monthX: Int, dayX: Int) {
        cleanDate = Calculations.cleanDate(dayX, monthX, yearX)
        tv_dateSelected.text = "Date: $cleanDate"
    }

    private fun getTimeCalendar() {
        val cal = Calendar.getInstance()
        hour = cal.get(Calendar.HOUR_OF_DAY)
        minute = cal.get(Calendar.MINUTE)
    }

    private fun getDateCalendar() {
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
    }
}
