package com.example.redcurtains

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PollsFragment : Fragment() {
    
    private lateinit var menuButton: ImageView
    private lateinit var profileButton: ImageView
    private lateinit var searchInput: EditText
    private lateinit var categoryFilterButton: MaterialButton
    private lateinit var popularFilterButton: MaterialButton
    private lateinit var newestFilterButton: MaterialButton
    private lateinit var communityPollsRecyclerView: RecyclerView
    private lateinit var trendingDiscussionsRecyclerView: RecyclerView
    private lateinit var createPollFab: FloatingActionButton
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_polls, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        initializeViews()
        setupClickListeners()
        setupRecyclerViews()
    }
    
    private fun initializeViews() {
        menuButton = view!!.findViewById(R.id.menuButton)
        profileButton = view!!.findViewById(R.id.profileButton)
        searchInput = view!!.findViewById(R.id.searchInput)
        categoryFilterButton = view!!.findViewById(R.id.categoryFilterButton)
        popularFilterButton = view!!.findViewById(R.id.popularFilterButton)
        newestFilterButton = view!!.findViewById(R.id.newestFilterButton)
        communityPollsRecyclerView = view!!.findViewById(R.id.communityPollsRecyclerView)
        trendingDiscussionsRecyclerView = view!!.findViewById(R.id.trendingDiscussionsRecyclerView)
        createPollFab = view!!.findViewById(R.id.createPollFab)
    }
    
    private fun setupClickListeners() {
        menuButton.setOnClickListener {
            // TODO: Open navigation drawer or menu
        }
        
        profileButton.setOnClickListener {
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            startActivity(intent)
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
            val intent = Intent(requireContext(), CreatePollActivity::class.java)
            startActivity(intent)
        }
    }
    
    private fun setupRecyclerViews() {
        // Setup Community Polls RecyclerView
        communityPollsRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val communityPollsAdapter = PollsAdapter(getCommunityPollsData())
        communityPollsRecyclerView.adapter = communityPollsAdapter
        
        // Setup Trending Discussions RecyclerView
        trendingDiscussionsRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
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
                    val intent = Intent(requireContext(), PollVoteActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PollViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_poll_card, parent, false)
            return PollViewHolder(view)
        }
        
        override fun onBindViewHolder(holder: PollViewHolder, position: Int) {
            holder.bind(polls[position])
        }
        
        override fun getItemCount(): Int = polls.size
    }
} 