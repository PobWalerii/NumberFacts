package com.example.numberfacts.ui.main

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import com.example.numberfacts.R
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        handleSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setFinishApp()
    }

    private fun handleSplashScreen() {
        val splashScreen = installSplashScreen()
        splashScreen.setOnExitAnimationListener { splashScreenViewProvider ->
            val slideUp = ObjectAnimator.ofFloat(
                splashScreenViewProvider.view,
                View.ALPHA,
                0f
            )
            slideUp.interpolator = LinearInterpolator()
            slideUp.duration = 1000L
            slideUp.doOnEnd { splashScreenViewProvider.remove() }
            slideUp.start()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.fragmentContainerView).navigateUp() || super.onSupportNavigateUp()
    }

    private fun setFinishApp() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val navController = findNavController(R.id.fragmentContainerView)
                if (navController.currentDestination?.id == R.id.queryFragment) {
                    AlertDialog.Builder(this@MainActivity)
                        .setTitle(R.string.title_exit)
                        .setMessage(R.string.text_exit)
                        .setPositiveButton(R.string.text_yes) { _, _ ->
                            finish()
                        }
                        .setNegativeButton(R.string.text_no, null)
                        .show()
                } else {
                    navController.navigateUp()
                }
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)
    }

}