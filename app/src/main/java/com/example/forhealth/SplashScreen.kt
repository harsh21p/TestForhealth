package com.example.forhealth
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.splash_screen.*
import java.util.*

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        val animation_logo = AnimationUtils.loadAnimation(this,R.anim.top_animation)
        val animation_tag = AnimationUtils.loadAnimation(this,R.anim.top2_animation)
        logo.startAnimation(animation_logo)
        tagline.startAnimation(animation_tag)
        val splashscreentimeout = 4000
        val homeIntent = Intent(this@SplashScreen, FirstPage::class.java)
        Handler().postDelayed({
            startActivity(homeIntent)
            finish()
        }, splashscreentimeout.toLong())
    }
}