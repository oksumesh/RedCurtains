package com.example.redcurtains

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class NotificationsActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Enable edge-to-edge display
        enableEdgeToEdge()
        
        // Make the activity full screen
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        setContentView(R.layout.activity_notifications)
        
        // Hide system UI for full screen
        hideSystemUI()
        
        // Set up edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            // Let the content extend to all edges
            insets
        }
        
        setupNavigation()
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
    
    private fun setupNavigation() {
        val homeButton: ImageView = findViewById(R.id.homeButton)
        val ticketsButton: ImageView = findViewById(R.id.ticketsButton)
        val searchButton: ImageView = findViewById(R.id.searchButton)
        val favoritesButton: ImageView = findViewById(R.id.favoritesButton)
        
        homeButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        
        ticketsButton.setOnClickListener {
            val intent = Intent(this, TicketsActivity::class.java)
            startActivity(intent)
            finish()
        }
        
        searchButton.setOnClickListener {
            val intent = Intent(this, PollsActivity::class.java)
            startActivity(intent)
            finish()
        }
        
        favoritesButton.setOnClickListener {
            val intent = Intent(this, FavoritesActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
} 