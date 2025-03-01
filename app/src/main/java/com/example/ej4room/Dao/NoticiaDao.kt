package com.example.ej4room.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ej4room.Entity.NoticiaEntity

@Dao
interface NoticiaDao {
    @Query("SELECT * FROM NoticiaEntity")
    suspend fun obtenerTodasLasNoticias(): MutableList<NoticiaEntity>
    @Insert
    suspend fun agregarNoticia(noticiaEntity: NoticiaEntity)
    @Update
    suspend fun actualizarNoticia(noticiaEntity: NoticiaEntity)
    @Delete
    suspend fun borrarNoticia(noticiaEntity: NoticiaEntity)
    @Query("DELETE FROM NoticiaEntity")
    suspend fun borrarTodasLasNoticias()
    @Query("UPDATE NoticiaEntity SET id = 0")
    suspend fun actualizarNoticias()
}
