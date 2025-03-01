package com.example.veterinarioapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.veterinarioapp.Entity.FavoritoEntity
import com.example.veterinarioapp.Entity.ReviewEntity
import com.example.veterinarioapp.Entity.UsuarioEntity
import com.example.veterinarioapp.data.Aplicacion
import com.example.veterinarioapp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), OnClickListener {
    private lateinit var binding: ActivityMainBinding

    private lateinit var layoutLineal: LinearLayoutManager
    private lateinit var adaptadorReviews: AdaptarReviews

    private  var usuario: UsuarioEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        // Forzamos el modo claro para toda la aplicación.
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usuario = intent.getSerializableExtra("Usuario") as? UsuarioEntity

        val botonFlotante = binding.addNew
        botonFlotante.setOnClickListener {
            Toast.makeText(this, "Creando review", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, AnadirReviewActivity::class.java)
            intent.putExtra("Usuario", usuario)
            startActivity(intent)
        }

        configurarRecyclerView()
    }

    private fun configurarRecyclerView() {
        adaptadorReviews = AdaptarReviews(mutableListOf(), this)
        layoutLineal = LinearLayoutManager(this)

        obtenerReviews()

        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = layoutLineal
            adapter = adaptadorReviews
        }
    }

    private fun obtenerReviews() {
        lifecycleScope.launch(Dispatchers.IO) {
            val reviews = Aplicacion
                .baseDeDatos
                .reviewDao()
                .obtenerTodasLasReviews()

            val favoritos = usuario?.let {
                Aplicacion
                    .baseDeDatos
                    .favoritoDao()
                    .obtenerTodosLosFavoritos(it.id)
            }

            reviews.forEach { review ->
                if (favoritos != null) {
                    review.esFavorita = favoritos.any { it.reviewId == review.id }
                }
            }

            withContext(Dispatchers.Main) {
                adaptadorReviews.establecerReviews(reviews)
            }
        }
    }

    override fun alHacerClic(reviewEntity: ReviewEntity) {
        val intent = Intent(this, ActualizarReviewActivity::class.java)
        intent.putExtra("Review", reviewEntity)
        intent.putExtra("Usuario", usuario)
        startActivity(intent)
    }

    override fun alDarleAFavorito(reviewEntity: ReviewEntity) {
        reviewEntity.esFavorita = !reviewEntity.esFavorita
        adaptadorReviews.actualizar(reviewEntity)
        lifecycleScope.launch(Dispatchers.IO) {
            // Esto asignará -1 en caso de que usuario sea null. (No debería)
            val favoritoEntity = FavoritoEntity(usuario?.id ?: -1, reviewEntity.id)
            if (reviewEntity.esFavorita) {
                Aplicacion
                    .baseDeDatos
                    .favoritoDao()
                    .agregarFavorito(favoritoEntity)
            } else {
                Aplicacion
                    .baseDeDatos
                    .favoritoDao()
                    .borrarFavorito(favoritoEntity)
            }
            Aplicacion
                .baseDeDatos
                .reviewDao()
                .actualizarReview(reviewEntity)
        }
    }

    override fun alEliminar(reviewEntity: ReviewEntity) {
        adaptadorReviews.eliminar(reviewEntity)
        lifecycleScope.launch(Dispatchers.IO) {
            Aplicacion
                .baseDeDatos
                .reviewDao()
                .borrarReview(reviewEntity)
        }
    }
}
