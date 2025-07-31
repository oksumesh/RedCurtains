package com.example.redcurtains

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView

class SignInActivity : AppCompatActivity() {
    
    private lateinit var emailInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var rememberMeCheckbox: MaterialCheckBox
    private lateinit var signInButton: MaterialButton
    private lateinit var forgotPasswordText: MaterialTextView
    private lateinit var registerText: MaterialTextView
    private lateinit var facebookButton: MaterialButton
    private lateinit var googleButton: MaterialButton
    private lateinit var appleButton: MaterialButton
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Enable edge-to-edge display
        enableEdgeToEdge()
        
        // Make the activity full screen
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        setContentView(R.layout.activity_sign_in)
        
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
        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        rememberMeCheckbox = findViewById(R.id.rememberMeCheckbox)
        signInButton = findViewById(R.id.signInButton)
        forgotPasswordText = findViewById(R.id.forgotPasswordText)
        registerText = findViewById(R.id.registerText)
        facebookButton = findViewById(R.id.facebookButton)
        googleButton = findViewById(R.id.googleButton)
        appleButton = findViewById(R.id.appleButton)
    }
    
    private fun setupClickListeners() {
        signInButton.setOnClickListener {
            handleSignIn()
        }
        
        forgotPasswordText.setOnClickListener {
            handleForgotPassword()
        }
        
        registerText.setOnClickListener {
            handleRegister()
        }
        
        facebookButton.setOnClickListener {
            handleSocialLogin("Facebook")
        }
        
        googleButton.setOnClickListener {
            handleSocialLogin("Google")
        }
        
        appleButton.setOnClickListener {
            handleSocialLogin("Apple")
        }
    }
    
    private fun handleSignIn() {
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()
        val rememberMe = rememberMeCheckbox.isChecked
        
        // Basic validation
        if (email.isEmpty()) {
            emailInput.error = "Email is required"
            return
        }
        
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.error = "Please enter a valid email"
            return
        }
        
        if (password.isEmpty()) {
            passwordInput.error = "Password is required"
            return
        }
        
        if (password.length < 6) {
            passwordInput.error = "Password must be at least 6 characters"
            return
        }
        
        // TODO: Implement actual authentication logic
        Toast.makeText(this, "Sign in successful!", Toast.LENGTH_SHORT).show()
        
        // Clear inputs after successful login
        emailInput.text?.clear()
        passwordInput.text?.clear()
        rememberMeCheckbox.isChecked = false
    }
    
    private fun handleForgotPassword() {
        // Navigate to ForgotPasswordActivity
        val intent = Intent(this, ForgotPasswordActivity::class.java)
        startActivity(intent)
    }
    
    private fun handleRegister() {
        // Navigate to SignUpActivity
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }
    
    private fun handleSocialLogin(provider: String) {
        Toast.makeText(this, "$provider login coming soon!", Toast.LENGTH_SHORT).show()
    }
} 