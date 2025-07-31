package com.example.redcurtains

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import android.widget.CheckBox

class CreatePasswordActivity : AppCompatActivity() {
    
    private lateinit var backButton: androidx.appcompat.widget.AppCompatImageView
    private lateinit var newPasswordInput: TextInputEditText
    private lateinit var confirmPasswordInput: TextInputEditText
    private lateinit var rememberMeCheckbox: CheckBox
    private lateinit var continueButton: MaterialButton
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Enable edge-to-edge display
        enableEdgeToEdge()
        
        // Make the activity full screen
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        setContentView(R.layout.activity_create_password)
        
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
            
            // Hide both status bar and navigation bar
            windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
            
            // Make the UI immersive
            windowInsetsController.systemBarsBehavior = 
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        } catch (e: Exception) {
            // Fallback to legacy method if modern API fails
            forceFullScreen()
        }
    }
    
    private fun forceFullScreen() {
        // Use legacy method as backup
        window.decorView.systemUiVisibility = (
            android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
            or android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            or android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
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
        backButton = findViewById(R.id.backButton)
        newPasswordInput = findViewById(R.id.newPasswordInput)
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput)
        rememberMeCheckbox = findViewById(R.id.rememberMeCheckbox)
        continueButton = findViewById(R.id.continueButton)
    }
    
    private fun setupClickListeners() {
        backButton.setOnClickListener {
            handleBackButton()
        }
        
        continueButton.setOnClickListener {
            handleContinueButton()
        }
    }
    
    private fun handleBackButton() {
        // Navigate back to previous screen
        finish()
    }
    
    private fun handleContinueButton() {
        val newPassword = newPasswordInput.text.toString().trim()
        val confirmPassword = confirmPasswordInput.text.toString().trim()
        val rememberMe = rememberMeCheckbox.isChecked
        
        // Validate passwords
        if (newPassword.isEmpty()) {
            newPasswordInput.error = "Please enter a new password"
            return
        }
        
        if (newPassword.length < 8) {
            newPasswordInput.error = "Password must be at least 8 characters"
            return
        }
        
        if (confirmPassword.isEmpty()) {
            confirmPasswordInput.error = "Please confirm your password"
            return
        }
        
        if (newPassword != confirmPassword) {
            confirmPasswordInput.error = "Passwords do not match"
            return
        }
        
        // TODO: Implement password update logic
        Toast.makeText(this, "Password updated successfully!", Toast.LENGTH_SHORT).show()
        
        // Navigate to main app or sign in screen
        finish()
    }
} 