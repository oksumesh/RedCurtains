package com.example.redcurtains

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LoyaltyRewardsActivity : AppCompatActivity() {
    
    private lateinit var backButton: ImageView
    private lateinit var helpButton: ImageView
    private lateinit var pointsBalance: TextView
    private lateinit var tierProgress: View
    private lateinit var earnPointsButton: View
    private lateinit var redeemRewardsButton: View
    private lateinit var availableRewardsRecyclerView: RecyclerView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Enable edge-to-edge display
        enableEdgeToEdge()
        
        // Make the activity full screen
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        setContentView(R.layout.activity_loyalty_rewards)
        
        // Hide system UI for full screen
        hideSystemUI()
        
        // Set up edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            // Let the content extend to all edges
            insets
        }
        
        initializeViews()
        setupClickListeners()
        setupRewardsRecyclerView()
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
        helpButton = findViewById(R.id.helpButton)
        pointsBalance = findViewById(R.id.pointsBalance)
        tierProgress = findViewById(R.id.tierProgress)
        earnPointsButton = findViewById(R.id.earnPointsButton)
        redeemRewardsButton = findViewById(R.id.redeemRewardsButton)
        availableRewardsRecyclerView = findViewById(R.id.availableRewardsRecyclerView)
    }
    
    private fun setupClickListeners() {
        backButton.setOnClickListener {
            finish()
        }
        
        helpButton.setOnClickListener {
            showHelpDialog()
        }
        
        earnPointsButton.setOnClickListener {
            showEarnPointsDialog()
        }
        
        redeemRewardsButton.setOnClickListener {
            showRedeemRewardsDialog()
        }
    }
    
    private fun setupRewardsRecyclerView() {
        availableRewardsRecyclerView.layoutManager = LinearLayoutManager(this)
        
        val rewards = listOf(
            Reward("Free Popcorn", "Enjoy a free medium popcorn with any movie ticket purchase", 200, "Available", R.drawable.ic_gift),
            Reward("Free Soda", "Get a free medium soda with any movie ticket", 150, "Available", R.drawable.ic_gift),
            Reward("Discount Ticket", "Get 20% off on your next movie ticket", 500, "Available", R.drawable.ic_ticket),
            Reward("VIP Seating", "Upgrade to premium seating for your next movie", 800, "Available", R.drawable.ic_star)
        )
        
        val adapter = RewardsAdapter(rewards) { reward ->
            onRewardClicked(reward)
        }
        availableRewardsRecyclerView.adapter = adapter
    }
    
    private fun showHelpDialog() {
        Toast.makeText(this, "Help: Tap on rewards to redeem them. Earn points by purchasing movie tickets!", Toast.LENGTH_LONG).show()
    }
    
    private fun showEarnPointsDialog() {
        Toast.makeText(this, "Earn Points: Purchase movie tickets to earn points. Each ticket gives you 150 points!", Toast.LENGTH_LONG).show()
    }
    
    private fun showRedeemRewardsDialog() {
        Toast.makeText(this, "Redeem Rewards: Tap on any available reward to redeem it using your points!", Toast.LENGTH_LONG).show()
    }
    
    private fun onRewardClicked(reward: Reward) {
        val currentPoints = pointsBalance.text.toString().replace(",", "").toIntOrNull() ?: 0
        
        if (currentPoints >= reward.pointsRequired) {
            // Show confirmation dialog
            showRedeemConfirmationDialog(reward)
        } else {
            Toast.makeText(this, "Not enough points! You need ${reward.pointsRequired} points to redeem this reward.", Toast.LENGTH_LONG).show()
        }
    }
    
    private fun showRedeemConfirmationDialog(reward: Reward) {
        val currentPoints = pointsBalance.text.toString().replace(",", "").toIntOrNull() ?: 0
        val newPoints = currentPoints - reward.pointsRequired
        
        // Update points display
        pointsBalance.text = formatPoints(newPoints)
        
        Toast.makeText(this, "Successfully redeemed ${reward.title}!", Toast.LENGTH_SHORT).show()
    }
    
    private fun formatPoints(points: Int): String {
        return String.format("%,d", points)
    }
    
    data class Reward(
        val title: String,
        val description: String,
        val pointsRequired: Int,
        val status: String,
        val iconResId: Int
    )
    
    inner class RewardsAdapter(
        private val rewards: List<Reward>,
        private val onRewardClick: (Reward) -> Unit
    ) : RecyclerView.Adapter<RewardsAdapter.RewardViewHolder>() {
        
        inner class RewardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val rewardIcon: ImageView = itemView.findViewById(R.id.rewardIcon)
            private val rewardTitle: TextView = itemView.findViewById(R.id.rewardTitle)
            private val rewardDescription: TextView = itemView.findViewById(R.id.rewardDescription)
            private val pointsRequired: TextView = itemView.findViewById(R.id.pointsRequired)
            private val rewardStatus: TextView = itemView.findViewById(R.id.rewardStatus)
            
            fun bind(reward: Reward) {
                rewardIcon.setImageResource(reward.iconResId)
                rewardTitle.text = reward.title
                rewardDescription.text = reward.description
                pointsRequired.text = "${reward.pointsRequired} pts"
                rewardStatus.text = reward.status
                
                itemView.setOnClickListener {
                    onRewardClick(reward)
                }
            }
        }
        
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RewardViewHolder {
            val view = layoutInflater.inflate(R.layout.item_reward_card, parent, false)
            return RewardViewHolder(view)
        }
        
        override fun onBindViewHolder(holder: RewardViewHolder, position: Int) {
            holder.bind(rewards[position])
        }
        
        override fun getItemCount(): Int = rewards.size
    }
} 