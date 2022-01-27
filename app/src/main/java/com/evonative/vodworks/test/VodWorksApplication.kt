package com.evonative.vodworks.test

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class VodWorksApplication: Application() {

    companion object {
        @JvmStatic
        private lateinit var instance: VodWorksApplication
        @JvmStatic
        fun getContext(): Context? = instance
    }
    override fun onCreate() {
        super.onCreate()
        instance = this;
    }


}