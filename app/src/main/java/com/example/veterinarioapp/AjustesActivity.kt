package com.example.veterinarioapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class AjustesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajustes)
    }

    fun volverAtras(view: View) {
        finish()
    }

}
