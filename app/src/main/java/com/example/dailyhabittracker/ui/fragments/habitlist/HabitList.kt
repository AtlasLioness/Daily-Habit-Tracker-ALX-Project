package com.example.dailyhabittracker.ui.fragments.habitlist

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.dailyhabittracker.R
import com.example.dailyhabittracker.data.models.Habit
import com.example.dailyhabittracker.ui.fragments.habitlist.adapters.HabitListAdapter
import com.example.dailyhabittracker.ui.viewmodels.HabitViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HabitList : Fragment(R.layout.fragment_habit_list) {

    private lateinit var fabAdd: FloatingActionButton
    private lateinit var habitViewModel: HabitViewModel
    private lateinit var adapter: HabitListAdapter
    private lateinit var rv_habits: RecyclerView
    private lateinit var tv_emptyView: TextView
    private lateinit var swipeToRefresh: SwipeRefreshLayout
    private lateinit var progressBarHabits: ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the ViewModel
        habitViewModel = ViewModelProvider(this).get(HabitViewModel::class.java)

        // Initialize the adapter with the ViewModel
        adapter = HabitListAdapter(habitViewModel)

        rv_habits = view.findViewById(R.id.rv_habits)
        tv_emptyView = view.findViewById(R.id.tv_emptyView)
        swipeToRefresh = view.findViewById(R.id.swipeToRefresh)
        progressBarHabits = view.findViewById(R.id.progressBarHabits)

        rv_habits.adapter = adapter
        rv_habits.layoutManager = LinearLayoutManager(requireContext())

        habitViewModel.getAllHabits.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
            updateProgressBar(it)

            if (it.isEmpty()) {
                rv_habits.visibility = View.GONE
                tv_emptyView.visibility = View.VISIBLE
            } else {
                rv_habits.visibility = View.VISIBLE
                tv_emptyView.visibility = View.GONE
            }
        })

        swipeToRefresh.setOnRefreshListener {
            // Refresh the data from the ViewModel
            habitViewModel.getAllHabits.value?.let { habits ->
                adapter.setData(habits)
                updateProgressBar(habits)
            }
            swipeToRefresh.isRefreshing = false
        }

        fabAdd = view.findViewById(R.id.fab_add)
        fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_habitList_to_createHabitItem)
        }

        // Setup the MenuProvider
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.nav_main, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.nav_delete -> {
                        habitViewModel.deleteAllHabits()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner)
    }

    private fun updateProgressBar(habits: List<Habit>) {
        if (habits.isNotEmpty()) {
            val checkedHabits = habits.count { it.isChecked }
            val progress = (checkedHabits.toFloat() / habits.size) * 100
            progressBarHabits.progress = progress.toInt()
        } else {
            progressBarHabits.progress = 0
        }
    }
}
