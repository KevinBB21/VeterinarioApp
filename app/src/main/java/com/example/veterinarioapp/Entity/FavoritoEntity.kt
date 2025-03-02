package com.example.veterinarioapp.Entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE

@Entity(
    tableName = "FavoritoEntity",
    primaryKeys = ["usuarioId", "reviewId"],
    foreignKeys = [
        ForeignKey(entity = UsuarioEntity::class, parentColumns = ["id"], childColumns = ["usuarioId"],
                onDelete = CASCADE),
        ForeignKey(entity = ReviewEntity::class, parentColumns = ["id"], childColumns = ["reviewId"],
                onDelete = CASCADE)
    ]
)
data class FavoritoEntity(
    val usuarioId: Long,
    val reviewId: Long
)