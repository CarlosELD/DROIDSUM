package com.example.Evaluacion_U1

import android.app.Application
import com.example.Evaluacion_U1.data.AppContainer
import com.example.Evaluacion_U1.data.DefaultAppContainer

class AlumnosApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}