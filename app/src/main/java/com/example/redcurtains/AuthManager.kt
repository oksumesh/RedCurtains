package com.example.redcurtains

import android.content.Context
import android.content.SharedPreferences

object AuthManager {
    private const val PREF_NAME = "RedCurtainsPrefs"
    private const val KEY_AUTH_TOKEN = "auth_token"
    private const val KEY_USER_EMAIL = "user_email"
    private const val KEY_IS_LOGGED_IN = "is_logged_in"
    private const val KEY_REMEMBER_ME = "remember_me"
    
    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }
    
    fun isLoggedIn(context: Context): Boolean {
        val prefs = getSharedPreferences(context)
        val authToken = prefs.getString(KEY_AUTH_TOKEN, null)
        val isLoggedIn = prefs.getBoolean(KEY_IS_LOGGED_IN, false)
        return authToken != null && isLoggedIn
    }
    
    fun saveLoginState(context: Context, email: String, rememberMe: Boolean) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(KEY_AUTH_TOKEN, "dummy_token_${System.currentTimeMillis()}")
        editor.putString(KEY_USER_EMAIL, email)
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.putBoolean(KEY_REMEMBER_ME, rememberMe)
        editor.apply()
    }
    
    fun clearLoginState(context: Context) {
        val editor = getSharedPreferences(context).edit()
        editor.clear()
        editor.apply()
    }
    
    fun getUserEmail(context: Context): String? {
        return getSharedPreferences(context).getString(KEY_USER_EMAIL, null)
    }
    
    fun getAuthToken(context: Context): String? {
        return getSharedPreferences(context).getString(KEY_AUTH_TOKEN, null)
    }
} 