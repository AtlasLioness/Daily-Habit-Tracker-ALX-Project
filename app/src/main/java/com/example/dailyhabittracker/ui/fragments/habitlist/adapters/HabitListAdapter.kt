package com.example.dailyhabittracker.ui.fragments.habitlist.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.dailyhabittracker.R
import com.example.dailyhabittracker.data.models.Habit
import com.example.dailyhabittracker.ui.fragments.habitlist.HabitListDirections
import com.example.dailyhabittracker.ui.viewmodels.HabitViewModel
import com.example.dailyhabittracker.utils.Calculations

class HabitListAdapter(
    private val viewModel: HabitViewModel
) : RecyclerView.Adapter<HabitListAdapter.MyViewHolder>() {

    var habitList = emptyList<Habit>()
    private val TAG = "HabitListAdapter"

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cvCardView: CardView = itemView.findViewById(R.id.cv_cardView)
        val checkBoxHabit: CheckBox = itemView.findViewById(R.id.checkBoxHabit)
        val titleTextView: TextView = itemView.findViewById(R.id.tv_item_title)
        val descriptionTextView: TextView = itemView.findViewById(R.id.tv_item_description)
        val timeElapsedTextView: TextView = itemView.findViewById(R.id.tv_timeElapsed)
        val createdTimeStampTextView: TextView = itemView.findViewById(R.id.tv_item_createdTimeStamp)

        init {
            cvCardView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    Log.d(TAG, "Item clicked at: $position ")
                    Log.d(TAG, "ID: ${habitList[position].id} ")
                    val action = HabitListDirections.actionHabitListToUpdateHabitItem(habitList[position])
                    itemView.findNavController().navigate(action)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_habit_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentHabit = habitList[position]
        holder.titleTextView.text = currentHabit.habit_title
        holder.descriptionTextView.text = currentHabit.habit_description

        // Handle potential parsing errors
        val timeElapsed = runCatching {
            Calculations.calculateTimeBetweenDates(currentHabit.habit_startTime)
        }.getOrElse {
            "Error calculating time"
        }
        holder.timeElapsedTextView.text = timeElapsed
        holder.createdTimeStampTextView.text = "Since: ${currentHabit.habit_startTime}"

        holder.checkBoxHabit.isChecked = currentHabit.isChecked

        holder.checkBoxHabit.setOnCheckedChangeListener { _, isChecked ->
            if (position != RecyclerView.NO_POSITION) {
                habitList[position].isChecked = isChecked
                viewModel.checkHabit(currentHabit, isChecked)
            }
        }
    }

    override fun getItemCount(): Int = habitList.size

    fun setData(habit: List<Habit>) {
        this.habitList = habit
        notifyDataSetChanged()
    }
}
