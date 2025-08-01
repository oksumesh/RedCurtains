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
import com.google.android.material.textview.MaterialTextView
import android.widget.EditText

class SignUpActivity : AppCompatActivity() {
    
    private lateinit var backButton: androidx.appcompat.widget.AppCompatImageView
    private lateinit var phoneNumberText: EditText
    private lateinit var continueButton: MaterialButton
    private lateinit var googleSignInButton: MaterialButton
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Enable edge-to-edge display
        enableEdgeToEdge()
        
        // Make the activity full screen
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        setContentView(R.layout.activity_sign_up)
        
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
        backButton = findViewById(R.id.backButton)
        phoneNumberText = findViewById(R.id.phoneNumberText)
        continueButton = findViewById(R.id.continueButton)
        googleSignInButton = findViewById(R.id.googleSignInButton)
    }
    
    private fun setupClickListeners() {
        backButton.setOnClickListener {
            handleBackButton()
        }
        
        continueButton.setOnClickListener {
            handleContinueButton()
        }
        
        googleSignInButton.setOnClickListener {
            handleGoogleSignIn()
        }
    }
    
    private fun handleBackButton() {
        // Navigate back to sign-in screen
        finish()
    }
    
    private fun handleContinueButton() {
        val phoneNumber = phoneNumberText.text.toString()
        
        if (phoneNumber.isEmpty()) {
            Toast.makeText(this, "Please enter a phone number", Toast.LENGTH_SHORT).show()
            return
        }
        
        // TODO: Implement phone number validation and verification
        Toast.makeText(this, "Phone verification coming soon!", Toast.LENGTH_SHORT).show()
    }
    
    private fun handleGoogleSignIn() {
        // TODO: Implement Google Sign-In
        Toast.makeText(this, "Google Sign-In coming soon!", Toast.LENGTH_SHORT).show()
    }
} 