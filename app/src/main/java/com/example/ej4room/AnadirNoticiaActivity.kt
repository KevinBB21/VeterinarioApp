package com.example.ej4room

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.example.ej4room.Entity.NoticiaEntity
import com.example.ej4room.Entity.UsuarioEntity
import com.example.ej4room.data.Aplicacion
import com.example.ej4room.databinding.ActivityAddNewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnadirNoticiaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNewBinding

    private  var usuario: UsuarioEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        // Forzamos el modo claro para toda la aplicación.
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
            val noticiaUrl = binding.insertarEnlace.text.toString()

            val noticia = NoticiaEntity(
                titulo = titulo,
                descripcion = descripcion,
                fecha = fecha,
                imagenUrl = imagenUrl,
                noticiaUrl = noticiaUrl)
            guardarDatos(noticia)

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("Usuario", usuario)
            startActivity(intent)

            Toast.makeText(this, "Noticia guardada", Toast.LENGTH_SHORT).show()
        }
    }

    private fun guardarDatos(noticiaEntity: NoticiaEntity) {
        lifecycleScope.launch(Dispatchers.IO) {
            Aplicacion
                .baseDeDatos
                .noticiaDao()
                .agregarNoticia(noticiaEntity)
        }
    }
}
