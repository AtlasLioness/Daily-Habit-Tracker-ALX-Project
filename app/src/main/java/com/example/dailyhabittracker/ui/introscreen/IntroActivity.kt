package com.example.dailyhabittracker.ui.introscreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.dailyhabittracker.R
import com.example.dailyhabittracker.data.models.IntroView
import com.example.dailyhabittracker.ui.MainActivity
import me.relex.circleindicator.CircleIndicator3

class IntroActivity : AppCompatActivity() {

    private lateinit var viewPager2: ViewPager2
    private lateinit var circleIndicator: CircleIndicator3
    private lateinit var btnStartApp: View
    private lateinit var introViews: List<IntroView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        addToIntroView()

        // Initialize views
        viewPager2 = findViewById(R.id.viewPager2)
        circleIndicator = findViewById(R.id.circleIndicator)
        btnStartApp = findViewById(R.id.btn_start_app)

        // Set button to invisible initially
        btnStartApp.visibility = View.INVISIBLE

        // Set up ViewPager2 with the adapter
        viewPager2.adapter = ViewPagerIntroAdapter(introViews)
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        // Set up CircleIndicator3
        circleIndicator.setViewPager(viewPager2)

        // Listen for page changes
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (position == introViews.size - 1) {
                    // Show the button when on the last page
                    animationButton()
                } else {
                    // Hide the button on all other pages
                    btnStartApp.visibility = View.INVISIBLE
                }
            }
        })
    }

    private fun animationButton() {
        if (btnStartApp.visibility != View.VISIBLE) {
            btnStartApp.visibility = View.VISIBLE
            btnStartApp.animate().apply {
                duration = 1400
                alpha(1f)
                withEndAction {
                    // Set click listener only once
                    btnStartApp.setOnClickListener {
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }.start()
        }
    }

    private fun addToIntroView() {
        // Add intro items for the viewpager
        introViews = listOf(
            IntroView("Hello Stranger !", R.drawable.full_logo),
            IntroView("Are you ready to change your life one habit at a time?", R.drawable.quote),
            IntroView("We believe in you! Tap on the button below to start your new life!", R.drawable.quote2)
        )
    }
}