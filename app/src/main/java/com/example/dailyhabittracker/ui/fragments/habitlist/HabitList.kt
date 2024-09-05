package com.example.dailyhabittracker.ui.fragments.habitlist


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.dailyhabittracker.R
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton


class HabitList : Fragment(R.layout.fragment_habit_list) {
    private lateinit var fabAdd: FloatingActionButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fabAdd = view.findViewById(R.id.fab_add)
        fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_habitList_to_createHabitItem)
        }
    }
}
