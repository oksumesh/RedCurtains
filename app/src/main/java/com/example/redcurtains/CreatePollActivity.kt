package com.example.redcurtains

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial

class CreatePollActivity : AppCompatActivity() {
    
    private lateinit var closeButton: ImageView
    private lateinit var pollTitleInput: EditText
    private lateinit var answerChoice1Input: EditText
    private lateinit var answerChoice2Input: EditText
    private lateinit var answerChoice3Input: EditText
    private lateinit var addAnswerChoiceButton: MaterialButton
    private lateinit var anonymousVotingSwitch: SwitchMaterial
    private lateinit var pollDurationContainer: LinearLayout
    private lateinit var visibilityContainer: LinearLayout
    private lateinit var publishPollButton: MaterialButton
    private lateinit var saveDraftButton: MaterialButton
    private lateinit var answerChoicesContainer: LinearLayout
    
    private var answerChoiceCount = 3
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Enable edge-to-edge display
        enableEdgeToEdge()
        
        // Make the activity full screen
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        setContentView(R.layout.activity_create_poll)
        
        // Hide system UI for full screen
        hideSystemUI()
        
        // Set up edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            // Let the content extend to all edges
            insets
        }
        
        initializeViews()
        setupClickListeners()
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
        pollTitleInput = findViewById(R.id.pollTitleInput)
        answerChoice1Input = findViewById(R.id.answerChoice1Input)
        answerChoice2Input = findViewById(R.id.answerChoice2Input)
        answerChoice3Input = findViewById(R.id.answerChoice3Input)
        addAnswerChoiceButton = findViewById(R.id.addAnswerChoiceButton)
        anonymousVotingSwitch = findViewById(R.id.anonymousVotingSwitch)
        pollDurationContainer = findViewById(R.id.pollDurationContainer)
        visibilityContainer = findViewById(R.id.visibilityContainer)
        publishPollButton = findViewById(R.id.publishPollButton)
        saveDraftButton = findViewById(R.id.saveDraftButton)
        answerChoicesContainer = findViewById(R.id.answerChoicesContainer)
    }
    
    private fun setupClickListeners() {
        closeButton.setOnClickListener {
            finish()
        }
        
        addAnswerChoiceButton.setOnClickListener {
            addAnswerChoice()
        }
        
        pollDurationContainer.setOnClickListener {
            // TODO: Show date/time picker dialog
        }
        
        visibilityContainer.setOnClickListener {
            // TODO: Show visibility options dialog
        }
        
        publishPollButton.setOnClickListener {
            publishPoll()
        }
        
        saveDraftButton.setOnClickListener {
            saveDraft()
        }
    }
    
    private fun addAnswerChoice() {
        if (answerChoiceCount < 6) { // Limit to 6 answer choices
            answerChoiceCount++
            
            // Create new answer choice input
            val newAnswerChoice = layoutInflater.inflate(R.layout.item_answer_choice, answerChoicesContainer, false)
            val answerInput = newAnswerChoice.findViewById<EditText>(R.id.answerChoiceInput)
            val removeButton = newAnswerChoice.findViewById<ImageView>(R.id.removeAnswerChoiceButton)
            
            // Set hint text
            answerInput.hint = "Answer Choice $answerChoiceCount"
            
            // Set remove button click listener
            removeButton.setOnClickListener {
                answerChoicesContainer.removeView(newAnswerChoice)
                answerChoiceCount--
                updateAddButtonVisibility()
            }
            
            // Add to container
            answerChoicesContainer.addView(newAnswerChoice)
            
            // Hide add button if max reached
            updateAddButtonVisibility()
        }
    }
    
    private fun updateAddButtonVisibility() {
        addAnswerChoiceButton.visibility = if (answerChoiceCount >= 6) View.GONE else View.VISIBLE
    }
    
    private fun publishPoll() {
        val pollTitle = pollTitleInput.text.toString().trim()
        val answer1 = answerChoice1Input.text.toString().trim()
        val answer2 = answerChoice2Input.text.toString().trim()
        val answer3 = answerChoice3Input.text.toString().trim()
        val isAnonymous = anonymousVotingSwitch.isChecked
        
        // Basic validation
        if (pollTitle.isEmpty()) {
            pollTitleInput.error = "Poll title is required"
            return
        }
        
        if (answer1.isEmpty() || answer2.isEmpty() || answer3.isEmpty()) {
            // Show error for empty answer choices
            if (answer1.isEmpty()) answerChoice1Input.error = "Required"
            if (answer2.isEmpty()) answerChoice2Input.error = "Required"
            if (answer3.isEmpty()) answerChoice3Input.error = "Required"
            return
        }
        
        // TODO: Implement actual poll publishing logic
        // For now, just show success and close
        finish()
    }
    
    private fun saveDraft() {
        // TODO: Implement draft saving logic
        finish()
    }
} 