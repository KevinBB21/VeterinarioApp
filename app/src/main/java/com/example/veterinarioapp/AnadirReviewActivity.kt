package com.example.veterinarioapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.example.veterinarioapp.Entity.ReviewEntity
import com.example.veterinarioapp.Entity.UsuarioEntity
import com.example.veterinarioapp.data.Aplicacion
import com.example.veterinarioapp.databinding.ActivityAddNewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnadirReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNewBinding

    private  var usuario: UsuarioEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usuario = intent.getSerializableExtra("Usuario") as? UsuarioEntity

        val botonGuardar: Button = binding.btnGuardar
        botonGuardar.setOnClickListener {
            val titulo = binding.insertarTitulo.text.toString()
            val descripcion = binding.insertarResumen.text.toString()
            val fecha = binding.insertarFecha.text.toString()
            val imagenUrl = binding.insertarImagen.text.toString()
            val reviewUrl = binding.insertarEnlace.text.toString()

            val review = ReviewEntity(
                titulo = titulo,
                descripcion = descripcion,
                fecha = fecha,
                imagenUrl = imagenUrl,
                reviewUrl = reviewUrl)
            guardarDatos(review)

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("Usuario", usuario)
            startActivity(intent)

            Toast.makeText(this, "Review guardada", Toast.LENGTH_SHORT).show()
        }
    }

    private fun guardarDatos(reviewEntity: ReviewEntity) {
        lifecycleScope.launch(Dispatchers.IO) {
            Aplicacion
                .baseDeDatos
                .reviewDao()
                .agregarReview(reviewEntity)
        }
    }
}
