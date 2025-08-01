package com.example.redcurtains

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.material.button.MaterialButton

class PollVoteActivity : AppCompatActivity() {
    
    private lateinit var closeButton: View
    private lateinit var pollQuestionText: TextView
    private lateinit var optionsRadioGroup: RadioGroup
    private lateinit var submitVoteButton: MaterialButton
    
    private var selectedOptionId: Int = -1
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Enable edge-to-edge display
        enableEdgeToEdge()
        
        // Make the activity full screen
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        setContentView(R.layout.activity_poll_vote)
        
        // Hide system UI for full screen
        hideSystemUI()
        
        // Set up edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            // Let the content extend to all edges
            insets
        }
        
        initializeViews()
        setupClickListeners()
        loadPollData()
    }
    
    private fun hideSystemUI() {
        try {
            val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
            
            // Only hide navigation bar, keep status bar visible
            windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())
            
            // Make the UI immersive
            windowInsetsController.systemBarsBehavior = 
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        } catch (e: Exception) {
            // Fallback to legacy method if modern API fails
            forceSemiFullScreen()
        }
    }
    
    private fun forceSemiFullScreen() {
        // Use legacy method as backup - only hide navigation bar
        window.decorView.systemUiVisibility = (
            android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            or android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        )
    }
    
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            // Re-hide system UI when app gains focus
            hideSystemUI()
        }
    }
    
    override fun onResume() {
        super.onResume()
        // Ensure full screen is maintained when resuming
        hideSystemUI()
    }
    
    private fun initializeViews() {
        closeButton = findViewById(R.id.closeButton)
        pollQuestionText = findViewById(R.id.pollQuestionText)
        optionsRadioGroup = findViewById(R.id.optionsRadioGroup)
        submitVoteButton = findViewById(R.id.submitVoteButton)
    }
    
    private fun setupClickListeners() {
        closeButton.setOnClickListener {
            finish()
        }
        
        optionsRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            selectedOptionId = checkedId
            updateSubmitButtonState()
        }
        
        submitVoteButton.setOnClickListener {
            submitVote()
        }
    }
    
    private fun loadPollData() {
        // TODO: Load actual poll data from intent or database
        // For now, using sample data
        pollQuestionText.text = getString(R.string.sample_poll_question)
        
        // Set first option as selected by default
        val firstRadioButton = findViewById<RadioButton>(R.id.option1RadioButton)
        firstRadioButton?.isChecked = true
        selectedOptionId = R.id.option1RadioButton
        updateSubmitButtonState()
    }
    
    private fun updateSubmitButtonState() {
        submitVoteButton.isEnabled = selectedOptionId != -1
    }
    
    private fun submitVote() {
        if (selectedOptionId == -1) {
            return
        }
        
        // TODO: Implement actual vote submission logic
        // For now, just show success and close
        finish()
    }
} 