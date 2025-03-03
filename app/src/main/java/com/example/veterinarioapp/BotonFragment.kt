package com.example.veterinarioapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class BotonFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_boton, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnIrAjustes: Button = view.findViewById(R.id.btnIrAjustes)
        btnIrAjustes.setOnClickListener {
            val intent = Intent(requireContext(), AjustesActivity::class.java)
            startActivity(intent)
        }
    }
}
