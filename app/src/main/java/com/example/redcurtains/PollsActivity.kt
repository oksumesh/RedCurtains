package com.example.redcurtains

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PollsActivity : AppCompatActivity() {
    
    private lateinit var menuButton: ImageView
    private lateinit var profileButton: ImageView
    private lateinit var searchInput: EditText
    private lateinit var categoryFilterButton: MaterialButton
    private lateinit var popularFilterButton: MaterialButton
    private lateinit var newestFilterButton: MaterialButton
    private lateinit var communityPollsRecyclerView: RecyclerView
    private lateinit var trendingDiscussionsRecyclerView: RecyclerView
    private lateinit var createPollFab: FloatingActionButton
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
        
        setContentView(R.layout.activity_polls)
        
        // Hide system UI for full screen
        hideSystemUI()
        
        // Set up edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            // Let the content extend to all edges
            insets
        }
        
        initializeViews()
        setupClickListeners()
        setupRecyclerViews()
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
        menuButton = findViewById(R.id.menuButton)
        profileButton = findViewById(R.id.profileButton)
        searchInput = findViewById(R.id.searchInput)
        categoryFilterButton = findViewById(R.id.categoryFilterButton)
        popularFilterButton = findViewById(R.id.popularFilterButton)
        newestFilterButton = findViewById(R.id.newestFilterButton)
        communityPollsRecyclerView = findViewById(R.id.communityPollsRecyclerView)
        trendingDiscussionsRecyclerView = findViewById(R.id.trendingDiscussionsRecyclerView)
        createPollFab = findViewById(R.id.createPollFab)
        homeButton = findViewById(R.id.homeButton)
        ticketsButton = findViewById(R.id.ticketsButton)
        searchButton = findViewById(R.id.searchButton)
        favoritesButton = findViewById(R.id.favoritesButton)
        notificationsButton = findViewById(R.id.notificationsButton)
    }
    
    private fun setupClickListeners() {
        menuButton.setOnClickListener {
            // TODO: Open navigation drawer or menu
        }
        
        profileButton.setOnClickListener {
            // TODO: Navigate to profile screen
        }
        
        categoryFilterButton.setOnClickListener {
            // TODO: Show category filter dialog
        }
        
        popularFilterButton.setOnClickListener {
            // TODO: Sort by popularity
        }
        
        newestFilterButton.setOnClickListener {
            // TODO: Sort by newest
        }
        
        createPollFab.setOnClickListener {
            // TODO: Navigate to create poll screen
        }
        
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
            // Already on search/polls screen
        }
        
        favoritesButton.setOnClickListener {
            val intent = Intent(this, FavoritesActivity::class.java)
            startActivity(intent)
            finish()
        }
        
        notificationsButton.setOnClickListener {
            val intent = Intent(this, NotificationsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    
    private fun setupRecyclerViews() {
        // Setup Community Polls RecyclerView
        communityPollsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val communityPollsAdapter = PollsAdapter(getCommunityPollsData())
        communityPollsRecyclerView.adapter = communityPollsAdapter
        
        // Setup Trending Discussions RecyclerView
        trendingDiscussionsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val trendingDiscussionsAdapter = PollsAdapter(getTrendingDiscussionsData())
        trendingDiscussionsRecyclerView.adapter = trendingDiscussionsAdapter
    }
    
    private fun getCommunityPollsData(): List<PollItem> {
        return listOf(
            PollItem(getString(R.string.action_movie_night), R.drawable.action_movie_poster),
            PollItem(getString(R.string.comedy_marathon), R.drawable.comedy_movie_poster),
            PollItem(getString(R.string.drama_film_discussion), R.drawable.drama_movie_poster)
        )
    }
    
    private fun getTrendingDiscussionsData(): List<PollItem> {
        return listOf(
            PollItem(getString(R.string.scifi_film_debate), R.drawable.scifi_movie_poster),
            PollItem(getString(R.string.thriller_movie_poll), R.drawable.thriller_movie_poster),
            PollItem(getString(R.string.animated_film_favorites), R.drawable.animated_movie_poster)
        )
    }
    
    data class PollItem(val title: String, val imageResource: Int)
    
    inner class PollsAdapter(private val polls: List<PollItem>) : 
        RecyclerView.Adapter<PollsAdapter.PollViewHolder>() {
        
        inner class PollViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val pollImage: ImageView = itemView.findViewById(R.id.pollImage)
            private val pollTitle: TextView = itemView.findViewById(R.id.pollTitle)
            
            fun bind(poll: PollItem) {
                pollImage.setImageResource(poll.imageResource)
                pollTitle.text = poll.title
                
                itemView.setOnClickListener {
                    // TODO: Navigate to poll detail screen
                }
            }
        }
        
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PollViewHolder {
            val view = layoutInflater.inflate(R.layout.item_poll_card, parent, false)
            return PollViewHolder(view)
        }
        
        override fun onBindViewHolder(holder: PollViewHolder, position: Int) {
            holder.bind(polls[position])
        }
        
        override fun getItemCount(): Int = polls.size
    }
} 