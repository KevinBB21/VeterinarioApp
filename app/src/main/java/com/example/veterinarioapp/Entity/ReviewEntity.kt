package com.example.veterinarioapp.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "ReviewEntity")
data class ReviewEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var titulo: String = "",
    var descripcion: String = "",
    var fecha: String = "",
    var esFavorita: Boolean = false,
    val imagenUrl: String = "",
    val reviewUrl: String = ""
): Serializable
