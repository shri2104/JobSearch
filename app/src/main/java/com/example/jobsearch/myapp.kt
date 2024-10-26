package com.example.jobsearch

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    // This class is required to initialize Hilt for the app
}
