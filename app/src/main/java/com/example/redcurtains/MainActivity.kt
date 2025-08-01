package com.example.redcurtains

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    
    private lateinit var homeButton: ImageView
    private lateinit var ticketsButton: ImageView
    private lateinit var searchButton: ImageView
    private lateinit var favoritesButton: ImageView
    private lateinit var notificationsButton: ImageView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Enable edge-to-edge display
        enableEdgeToEdge()
        
        // Make the activity full screen
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        setContentView(R.layout.activity_main)
        
        // Hide system UI for full screen
        hideSystemUI()
        
        // Set up edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            // Let the content extend to all edges
            insets
        }
        
        initializeViews()
        setupNavigation()
        
        // Set default fragment (Home)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, HomeFragment())
                .commit()
        }
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
        homeButton = findViewById(R.id.homeButton)
        ticketsButton = findViewById(R.id.ticketsButton)
        searchButton = findViewById(R.id.searchButton)
        favoritesButton = findViewById(R.id.favoritesButton)
        notificationsButton = findViewById(R.id.notificationsButton)
    }
    
    private fun setupNavigation() {
        homeButton.setOnClickListener {
            switchFragment(HomeFragment())
            updateTabSelection(homeButton, true)
            updateTabSelection(ticketsButton, false)
            updateTabSelection(searchButton, false)
            updateTabSelection(favoritesButton, false)
            updateTabSelection(notificationsButton, false)
        }
        
        ticketsButton.setOnClickListener {
            switchFragment(TicketsFragment())
            updateTabSelection(homeButton, false)
            updateTabSelection(ticketsButton, true)
            updateTabSelection(searchButton, false)
            updateTabSelection(favoritesButton, false)
            updateTabSelection(notificationsButton, false)
        }
        
        searchButton.setOnClickListener {
            switchFragment(PollsFragment())
            updateTabSelection(homeButton, false)
            updateTabSelection(ticketsButton, false)
            updateTabSelection(searchButton, true)
            updateTabSelection(favoritesButton, false)
            updateTabSelection(notificationsButton, false)
        }
        
        favoritesButton.setOnClickListener {
            switchFragment(FavoritesFragment())
            updateTabSelection(homeButton, false)
            updateTabSelection(ticketsButton, false)
            updateTabSelection(searchButton, false)
            updateTabSelection(favoritesButton, true)
            updateTabSelection(notificationsButton, false)
        }
        
        notificationsButton.setOnClickListener {
            switchFragment(NotificationsFragment())
            updateTabSelection(homeButton, false)
            updateTabSelection(ticketsButton, false)
            updateTabSelection(searchButton, false)
            updateTabSelection(favoritesButton, false)
            updateTabSelection(notificationsButton, true)
        }
        
        // Set initial selection (Home)
        updateTabSelection(homeButton, true)
    }
    
    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
    
    private fun updateTabSelection(button: ImageView, isSelected: Boolean) {
        button.setColorFilter(
            if (isSelected) getColor(R.color.accent_orange) else getColor(R.color.text_secondary)
        )
    }
}