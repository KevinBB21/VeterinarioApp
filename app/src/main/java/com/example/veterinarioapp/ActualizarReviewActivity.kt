package com.example.veterinarioapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.example.veterinarioapp.Entity.ReviewEntity
import com.example.veterinarioapp.Entity.UsuarioEntity
import com.example.veterinarioapp.data.Aplicacion
import com.example.veterinarioapp.databinding.ActivityActualizarReviewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActualizarReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityActualizarReviewBinding

    private  var usuario: UsuarioEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        // Forzamos el modo claro para toda la aplicación.
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)

        usuario = intent.getSerializableExtra("Usuario") as? UsuarioEntity

        binding = ActivityActualizarReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val reviewObtenida = intent.getSerializableExtra("Review") as ReviewEntity

        val titulo: String = reviewObtenida.titulo
        val descripcion: String = reviewObtenida.descripcion
        val fecha: String = reviewObtenida.fecha

        val et_titulo = binding.insertarTitulo
        val et_descripcion = binding.insertarResumen
        val et_fecha = binding.insertarFecha
        val btn_actualizar = binding.btnGuardar

        et_titulo.setText(titulo)
        et_descripcion.setText(descripcion)
        et_fecha.setText(fecha)

        btn_actualizar.setOnClickListener {
            val id = reviewObtenida.id
            val esFavorita = reviewObtenida.esFavorita
            val imagenUrl = reviewObtenida.imagenUrl
            val reviewUrl = reviewObtenida.reviewUrl
            val reviewActualizada = ReviewEntity(
                id = id,
                titulo = et_titulo.text.toString(),
                descripcion = et_descripcion.text.toString(),
                fecha = et_fecha.text.toString(),
                esFavorita = esFavorita,
                imagenUrl = imagenUrl,
                reviewUrl = reviewUrl)
            guardarReview(reviewActualizada)
            intent = Intent(this, MainActivity::class.java)
            intent.putExtra("Usuario", usuario)
            startActivity(intent)
        }
    }

    private fun guardarReview(reviewActualizada: ReviewEntity) {
        lifecycleScope.launch(Dispatchers.IO) {
            Aplicacion
                .baseDeDatos
                .reviewDao()
                .actualizarReview(reviewActualizada)
        }
    }
}