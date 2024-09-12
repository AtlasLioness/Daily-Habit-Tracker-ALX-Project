package com.example.dailyhabittracker.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dailyhabittracker.data.database.HabitDatabase
import com.example.dailyhabittracker.data.models.Habit
import com.example.dailyhabittracker.logic.dao.HabitDao
import com.example.dailyhabittracker.logic.repository.HabitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HabitViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: HabitRepository
    val getAllHabits: LiveData<List<Habit>>

    init {
        val habitDao : HabitDao = HabitDatabase.getDatabase(application).habitDao()
        repository = HabitRepository(habitDao)
        getAllHabits = repository.getAllHabits
    }
    // Method to update progress
    fun updateProgressBar() {
        viewModelScope.launch(Dispatchers.IO) {
            val allHabits = getAllHabits.value ?: return@launch
            val totalHabits = allHabits.size
            val checkedHabits = allHabits.count { it.isChecked }

            // Cast to Float to ensure accurate division
            val progress = if (totalHabits > 0) (checkedHabits.toFloat() / totalHabits.toFloat()) * 100 else 0f

            // Notify observers with the progress value
            _progressBarValue.postValue(progress)
        }
    }
    private val _progressBarValue = MutableLiveData<Float>()
    val progressBarValue: LiveData<Float> = _progressBarValue

    fun addHabit(habit: Habit) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addHabit(habit)
        }
    }

    fun updateHabit(habit: Habit) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateHabit(habit)
        }
    }

    fun deleteHabit(habit: Habit) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteHabit(habit)
        }
    }

    fun deleteAllHabits() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllHabits()
        }
    }

    fun checkHabit(habit: Habit, isChecked: Boolean) = viewModelScope.launch {
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        habit.isChecked = isChecked
        habit.lastCheckedDate = currentDate
        repository.updateHabit(habit)
        updateProgressBar()
    }

    // New method to get a habit by its ID
    suspend fun getHabitById(id: Int): Habit? {
        return repository.getHabitById(id)
    }

}

