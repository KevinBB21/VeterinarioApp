package com.example.veterinarioapp.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.veterinarioapp.R

class EmergenciaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_emergencia, container, false)

        val btnLlamar = view.findViewById<Button>(R.id.btnLlamar)
        btnLlamar.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:123456789") // Número de emergencia
            startActivity(intent)
        }

        return view
    }
}
