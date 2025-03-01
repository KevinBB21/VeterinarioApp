package com.example.veterinarioapp.Entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE

@Entity(
    tableName = "FavoritoEntity",
    primaryKeys = ["usuarioId", "reviewId"],
    foreignKeys = [
        ForeignKey(entity = UsuarioEntity::class, parentColumns = ["id"], childColumns = ["usuarioId"],
                onDelete = CASCADE), // Esto borra los favoritos si se borra la review
        ForeignKey(entity = ReviewEntity::class, parentColumns = ["id"], childColumns = ["reviewId"],
                onDelete = CASCADE) // Esto borra los favoritos si se borra la review
    ]
)
data class FavoritoEntity(
    val usuarioId: Long,
    val reviewId: Long
)