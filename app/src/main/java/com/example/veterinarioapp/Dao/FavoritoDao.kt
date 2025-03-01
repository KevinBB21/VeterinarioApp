package com.example.veterinarioapp.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.veterinarioapp.Entity.FavoritoEntity

@Dao
interface FavoritoDao {
    @Query("SELECT * FROM FavoritoEntity WHERE usuarioId = :userId")
    suspend fun obtenerTodosLosFavoritos(userId: Long): MutableList<FavoritoEntity>
    @Insert
    suspend fun agregarFavorito(favoritoEntity: FavoritoEntity)
    @Delete
    suspend fun borrarFavorito(favoritoEntity: FavoritoEntity)
}