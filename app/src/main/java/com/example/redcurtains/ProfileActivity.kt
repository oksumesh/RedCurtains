package com.example.redcurtains

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class ProfileActivity : AppCompatActivity() {
    
    private lateinit var backButton: ImageView
    private lateinit var profileImage: ImageView
    private lateinit var userName: TextView
    private lateinit var userEmail: TextView
    private lateinit var loyaltyRewardsOption: LinearLayout
    private lateinit var editProfileOption: LinearLayout
    private lateinit var settingsOption: LinearLayout
    private lateinit var helpSupportOption: LinearLayout
    private lateinit var logoutOption: LinearLayout
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Enable edge-to-edge display
        enableEdgeToEdge()
        
        // Make the activity full screen
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        setContentView(R.layout.activity_profile)
        
        // Hide system UI for full screen
        hideSystemUI()
        
        // Set up edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            // Let the content extend to all edges
            insets
        }
        
        initializeViews()
        setupClickListeners()
        loadUserData()
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
        profileImage = findViewById(R.id.profileImage)
        userName = findViewById(R.id.userName)
        userEmail = findViewById(R.id.userEmail)
        loyaltyRewardsOption = findViewById(R.id.loyaltyRewardsOption)
        editProfileOption = findViewById(R.id.editProfileOption)
        settingsOption = findViewById(R.id.settingsOption)
        helpSupportOption = findViewById(R.id.helpSupportOption)
        logoutOption = findViewById(R.id.logoutOption)
    }
    
    private fun setupClickListeners() {
        backButton.setOnClickListener {
            finish()
        }
        
        loyaltyRewardsOption.setOnClickListener {
            val intent = Intent(this, LoyaltyRewardsActivity::class.java)
            startActivity(intent)
        }
        
        editProfileOption.setOnClickListener {
            // TODO: Navigate to edit profile screen
        }
        
        settingsOption.setOnClickListener {
            // TODO: Navigate to settings screen
        }
        
        helpSupportOption.setOnClickListener {
            // TODO: Navigate to help & support screen
        }
        
        logoutOption.setOnClickListener {
            // TODO: Implement logout functionality
        }
    }
    
    private fun loadUserData() {
        // TODO: Load actual user data from database or preferences
        // For now, using sample data
        userName.text = getString(R.string.sample_user_name)
        userEmail.text = getString(R.string.sample_user_email)
    }
} 