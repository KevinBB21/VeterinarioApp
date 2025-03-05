package com.example.veterinarioapp

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.veterinarioapp.Entity.FavoritoEntity
import com.example.veterinarioapp.Entity.ReviewEntity
import com.example.veterinarioapp.Entity.UsuarioEntity
import com.example.veterinarioapp.data.Aplicacion
import com.example.veterinarioapp.databinding.ActivityMainBinding
import com.example.veterinarioapp.fragments.AdopcionFragment
import com.example.veterinarioapp.fragments.EmergenciaFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var layoutLineal: LinearLayoutManager
    private lateinit var adaptadorReviews: AdaptarReviews
    private var usuario: UsuarioEntity? = null
    private lateinit var mediaPlayer: MediaPlayer
    private val PREFS_NAME = "MusicPrefs"
    private val PREFS_KEY_POSITION = "musicPosition"

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usuario = intent.getSerializableExtra("Usuario") as? UsuarioEntity

        configurarBottomNavigation()
        configurarRecyclerView()
        configurarMusica()

        // Cargar fragmento inicial (EmergenciaFragment por defecto)
        loadFragment(EmergenciaFragment())
    }

    /**
     * Configura el BottomNavigationView y su listener de clics
     */
    private fun configurarBottomNavigation() {
        val bottomNavigationView = binding.bottomNavigationView

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_emergencia -> loadFragment(EmergenciaFragment())
                R.id.nav_adopcion -> loadFragment(AdopcionFragment())
            }
            true
        }
    }

    /**
     * Carga un Fragment en el contenedor principal
     */
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    /**
     * Configura y muestra las reviews en un RecyclerView
     */
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

    /**
     * Obtiene las reviews de la base de datos
     */
    private fun obtenerReviews() {
        lifecycleScope.launch(Dispatchers.IO) {
            val reviews = Aplicacion.baseDeDatos.reviewDao().obtenerTodasLasReviews()
            val favoritos = usuario?.let {
                Aplicacion.baseDeDatos.favoritoDao().obtenerTodosLosFavoritos(it.id)
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

    /**
     * Configura la reproducción de música en segundo plano
     */
    private fun configurarMusica() {
        mediaPlayer = MediaPlayer.create(this, R.raw.alesso)
        mediaPlayer.isLooping = true

        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val savedPosition = sharedPreferences.getInt(PREFS_KEY_POSITION, 0)

        mediaPlayer.seekTo(savedPosition)
        mediaPlayer.start()
    }

    override fun onPause() {
        super.onPause()

        val position = mediaPlayer.currentPosition
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(PREFS_KEY_POSITION, position)
        editor.apply()

        mediaPlayer.pause()
    }

    override fun onResume() {
        super.onResume()
        mediaPlayer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    // Métodos de la interfaz OnClickListener para manejar interacciones con reviews
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
            val favoritoEntity = FavoritoEntity(usuario?.id ?: -1, reviewEntity.id)
            if (reviewEntity.esFavorita) {
                Aplicacion.baseDeDatos.favoritoDao().agregarFavorito(favoritoEntity)
            } else {
                Aplicacion.baseDeDatos.favoritoDao().borrarFavorito(favoritoEntity)
            }
            Aplicacion.baseDeDatos.reviewDao().actualizarReview(reviewEntity)
        }
    }

    override fun alEliminar(reviewEntity: ReviewEntity) {
        adaptadorReviews.eliminar(reviewEntity)
        lifecycleScope.launch(Dispatchers.IO) {
            Aplicacion.baseDeDatos.reviewDao().borrarReview(reviewEntity)
        }
    }
}
