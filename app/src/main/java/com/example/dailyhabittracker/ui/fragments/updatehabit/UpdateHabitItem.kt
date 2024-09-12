package com.example.dailyhabittracker.ui.fragments.updatehabit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.dailyhabittracker.R
import com.example.dailyhabittracker.data.models.Habit
import com.example.dailyhabittracker.ui.viewmodels.HabitViewModel
import com.example.dailyhabittracker.utils.Calculations
import java.util.Calendar

/**
 * A simple [Fragment] subclass.
 * Use the [UpdateHabitItem.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdateHabitItem : Fragment(R.layout.fragment_update_habit_item),
    TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private var title = ""
    private var description = ""
    private var timeStamp = ""

    private val args by navArgs<UpdateHabitItemArgs>()
    private lateinit var habitViewModel: HabitViewModel

    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minute = 0

    private var cleanDate = ""
    private var cleanTime = ""

    // Initialize buttons and text views
    private lateinit var btn_confirm: Button
    private lateinit var btn_confirm_update: Button
    private lateinit var btn_pickDate_update: Button
    private lateinit var btn_pickTime_update: Button
    private lateinit var tv_timeSelected_update: TextView
    private lateinit var tv_dateSelected_update: TextView
    private lateinit var et_habitTitle_update: EditText
    private lateinit var et_habitDescription_update: EditText


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        habitViewModel = ViewModelProvider(this).get(HabitViewModel::class.java)

        // Initialize buttons and text views
        btn_confirm = view.findViewById(R.id.btn_confirm)
        btn_confirm_update = view.findViewById(R.id.btn_confirm_update)
        btn_pickDate_update = view.findViewById(R.id.btn_pickDate_update)
        btn_pickTime_update = view.findViewById(R.id.btn_pickTime_update)
        tv_timeSelected_update = view.findViewById(R.id.tv_timeSelected_update)
        tv_dateSelected_update = view.findViewById(R.id.tv_dateSelected_update)
        et_habitTitle_update = view.findViewById(R.id.et_habitTitle_update)
        et_habitDescription_update = view.findViewById(R.id.et_habitDescription_update)

        //Retrieve data from our habit list
        et_habitTitle_update.setText(args.selectedHabit.habit_title)
        et_habitDescription_update.setText(args.selectedHabit.habit_description)


        //Pick the date and time again
        pickDateAndTime()

        //Confirm changes and update the selected item
        btn_confirm_update.setOnClickListener {
            updateHabit()
        }

        //Show the options menu in this fragment
        setHasOptionsMenu(true)
    }

    private fun updateHabit() {
        //Get text from editTexts
        title = et_habitTitle_update.text.toString()
        description = et_habitDescription_update.text.toString()

        //Create a timestamp string for our recyclerview
        timeStamp = "$cleanDate $cleanTime"

        //Check that the form is complete before submitting data to the database
        if (!(title.isEmpty() || description.isEmpty() || timeStamp.isEmpty() )) {
            val habit =
                Habit(args.selectedHabit.id, title, description, timeStamp)

            //add the habit if all the fields are filled
            habitViewModel.updateHabit(habit)
            Toast.makeText(context, "Habit updated! successfully!", Toast.LENGTH_SHORT).show()

            //navigate back to our home fragment
            findNavController().navigate(R.id.action_updateHabitItem_to_habitList)
        } else {
            Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
        }
    }



    //Handle date and time picking
    @RequiresApi(Build.VERSION_CODES.N)
    //set on click listeners for our data and time pickers
    private fun pickDateAndTime() {
        btn_pickDate_update.setOnClickListener {
            getDateCalendar()
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }

        btn_pickTime_update.setOnClickListener {
            getTimeCalendar()
            TimePickerDialog(context, this, hour, minute, true).show()
        }

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

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        cleanTime = Calculations.cleanTime(p1, p2)
        tv_timeSelected_update.text = "Time: $cleanTime"
    }

    override fun onDateSet(p0: DatePicker?, yearX: Int, monthX: Int, dayX: Int) {
        cleanDate = Calculations.cleanDate(dayX, monthX, yearX)
        tv_dateSelected_update.text = "Date: $cleanDate"
    }
    //------------------------------------------

    //Create options menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.single_item_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_delete -> {
                deleteHabit(args.selectedHabit)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    //------------------------------------------

    //Delete a single Habit
    private fun deleteHabit(habit: Habit) {
        habitViewModel.deleteHabit(habit)
        Toast.makeText(context, "Habit successfully deleted!", Toast.LENGTH_SHORT).show()

        findNavController().navigate(R.id.action_updateHabitItem_to_habitList)
    }
    //------------------------------------------
}