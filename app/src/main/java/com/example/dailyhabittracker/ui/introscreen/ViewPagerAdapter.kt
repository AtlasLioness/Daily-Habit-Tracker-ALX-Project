package com.example.dailyhabittracker.ui.introscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dailyhabittracker.data.models.IntroView
import com.example.dailyhabittracker.databinding.IntroItemPageBinding

class ViewPagerIntroAdapter(private val introViews: List<IntroView>) :
    RecyclerView.Adapter<ViewPagerIntroAdapter.IntroViewHolder>() {

    inner class IntroViewHolder(private val binding: IntroItemPageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(introView: IntroView) {
            binding.ivImageIntro.setImageResource(introView.image)
            binding.tvDescriptionIntro.text = introView.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroViewHolder {
        val binding = IntroItemPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IntroViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IntroViewHolder, position: Int) {
        val currentView = introViews[position]
        holder.bind(currentView)
    }

    override fun getItemCount(): Int {
        return introViews.size
    }
}
