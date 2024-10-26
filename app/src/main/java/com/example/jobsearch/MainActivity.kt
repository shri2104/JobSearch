package com.example.jobsearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.jobsearch.navigation.AppNavigation

import com.example.jobsearch.ui.theme.JobsearchTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Custom method for edge-to-edge display
        setContent {
            JobsearchTheme { // Apply your custom theme
                AppNavigation()
            }
        }
    }
}
