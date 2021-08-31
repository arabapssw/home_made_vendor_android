package com.homemade.login

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.homemade.home.HomeActivity
import com.test.utils.*
import org.koin.android.ext.android.inject

class LoginActivity : AppCompatActivity() {
    lateinit var skip:TextView
    private val sharePrefrence: SharedPreferences.Editor by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        skip= findViewById<TextView>(R.id.tv_skip)
        skip.setOnClickListener {
            clearTokens()
            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
            finish()
        }

    }

    fun showSkip() {
        skip.visibility = View.VISIBLE
    }
    fun removeSkip() {
        skip.visibility = View.GONE
    }
    private fun clearTokens() {
        sharePrefrence.remove(TOKEN_USER).remove(USER_ISVERIFIED).remove(CART_COUNTER)
            .remove(USER_DATA).remove(WALLET_BALANCE).apply()
    }

}