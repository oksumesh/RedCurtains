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
import android.widget.TextView
import android.widget.ProgressBar
import android.view.View

class SignUpActivity : AppCompatActivity() {
    
    private lateinit var backButton: androidx.appcompat.widget.AppCompatImageView
    private lateinit var emailText: EditText
    private lateinit var emailErrorText: TextView
    private lateinit var continueButton: MaterialButton
    private lateinit var loadingProgressBar: ProgressBar
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
        setupEmailValidation()
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
        emailText = findViewById(R.id.emailText)
        emailErrorText = findViewById(R.id.emailErrorText)
        continueButton = findViewById(R.id.continueButton)
        loadingProgressBar = findViewById(R.id.loadingProgressBar)
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
        val email = emailText.text.toString().trim()
        
        if (!isEmailValid(email)) {
            return
        }
        
        // Show loading state
        showLoadingState(true)
        
        // Simulate API call
        emailText.postDelayed({
            // TODO: Implement actual email verification with backend
            showLoadingState(false)
            Toast.makeText(this, "Email verification coming soon!", Toast.LENGTH_SHORT).show()
        }, 2000) // 2 second delay to simulate network call
    }
    
    private fun isEmailValid(email: String): Boolean {
        if (email.isEmpty()) {
            showEmailError("Please enter an email address")
            return false
        }
        
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showEmailError("Please enter a valid email address")
            return false
        }
        
        clearEmailError()
        return true
    }
    
    private fun showEmailError(message: String) {
        emailErrorText.text = message
        emailErrorText.visibility = View.VISIBLE
    }
    
    private fun clearEmailError() {
        emailErrorText.visibility = View.GONE
        emailErrorText.text = ""
    }
    
    private fun showLoadingState(show: Boolean) {
        if (show) {
            continueButton.isEnabled = false
            continueButton.text = "Verifying..."
            loadingProgressBar.visibility = View.VISIBLE
        } else {
            continueButton.isEnabled = true
            continueButton.text = getString(R.string.continue_text)
            loadingProgressBar.visibility = View.GONE
        }
    }
    
    private fun setupEmailValidation() {
        emailText.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: android.text.Editable?) {
                val email = s.toString().trim()
                if (email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    clearEmailError()
                }
            }
        })
    }
    
    private fun handleGoogleSignIn() {
        // TODO: Implement Google Sign-In
        Toast.makeText(this, "Google Sign-In coming soon!", Toast.LENGTH_SHORT).show()
    }
} 