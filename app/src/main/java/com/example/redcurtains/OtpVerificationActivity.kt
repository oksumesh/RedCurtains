package com.example.redcurtains

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import java.util.*

class OtpVerificationActivity : AppCompatActivity() {
    
    private lateinit var backButton: androidx.appcompat.widget.AppCompatImageView
    private lateinit var otpField1: EditText
    private lateinit var otpField2: EditText
    private lateinit var otpField3: EditText
    private lateinit var otpField4: EditText
    private lateinit var resendTimer: MaterialTextView
    private lateinit var verifyButton: MaterialButton
    
    private var countDownTimer: Timer? = null
    private var timeLeft = 44 // seconds
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Enable edge-to-edge display
        enableEdgeToEdge()
        
        // Make the activity full screen
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        setContentView(R.layout.activity_otp_verification)
        
        // Hide system UI for full screen
        hideSystemUI()
        
        // Set up edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            // Let the content extend to all edges
            insets
        }
        
        initializeViews()
        setupClickListeners()
        setupOtpInputs()
        startTimer()
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
        otpField1 = findViewById(R.id.otpField1)
        otpField2 = findViewById(R.id.otpField2)
        otpField3 = findViewById(R.id.otpField3)
        otpField4 = findViewById(R.id.otpField4)
        resendTimer = findViewById(R.id.resendTimer)
        verifyButton = findViewById(R.id.verifyButton)
    }
    
    private fun setupClickListeners() {
        backButton.setOnClickListener {
            handleBackButton()
        }
        
        verifyButton.setOnClickListener {
            handleVerifyButton()
        }
    }
    
    private fun setupOtpInputs() {
        // Set up text watchers for auto-focus
        otpField1.addTextChangedListener(createTextWatcher(otpField1, otpField2))
        otpField2.addTextChangedListener(createTextWatcher(otpField2, otpField3))
        otpField3.addTextChangedListener(createTextWatcher(otpField3, otpField4))
        otpField4.addTextChangedListener(createTextWatcher(otpField4, null))
        
        // Set initial focus to first field
        otpField1.requestFocus()
    }
    
    private fun createTextWatcher(currentField: EditText, nextField: EditText?): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s?.length == 1) {
                    nextField?.requestFocus()
                }
            }
        }
    }
    
    private fun startTimer() {
        countDownTimer = Timer()
        countDownTimer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    if (timeLeft > 0) {
                        timeLeft--
                        resendTimer.text = "Resend code in ${timeLeft}s"
                    } else {
                        resendTimer.text = "Resend code"
                        countDownTimer?.cancel()
                    }
                }
            }
        }, 0, 1000)
    }
    
    private fun handleBackButton() {
        // Navigate back to forgot password screen
        finish()
    }
    
    private fun handleVerifyButton() {
        val otp = otpField1.text.toString() + otpField2.text.toString() + 
                  otpField3.text.toString() + otpField4.text.toString()
        
        if (otp.length == 4) {
            // TODO: Implement OTP verification logic
            Toast.makeText(this, "OTP verified successfully!", Toast.LENGTH_SHORT).show()
            // Navigate to create password screen
            val intent = android.content.Intent(this, CreatePasswordActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Please enter complete OTP", Toast.LENGTH_SHORT).show()
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }
} 