package com.example.dailyhabittracker.logic.repository

import androidx.lifecycle.LiveData
import com.example.dailyhabittracker.data.models.Habit
import com.example.dailyhabittracker.logic.dao.HabitDao

class HabitRepository (private val habitDao: HabitDao) {
    val getAllHabits: LiveData<List<Habit>> = habitDao.getAllHabits()

    suspend fun addHabit(habit: Habit) {
        habitDao.addHabit(habit)
    }

    suspend fun updateHabit(habit: Habit) {
        habitDao.updateHabit(habit)
    }

    suspend fun deleteHabit(habit: Habit) {
        habitDao.deleteHabit(habit)
    }

    suspend fun deleteAllHabits() {
        habitDao.deleteAll()
    }

    // New method to get a habit by its ID
    suspend fun getHabitById(id: Int): Habit? {
        return habitDao.getHabitById(id)
    }
}