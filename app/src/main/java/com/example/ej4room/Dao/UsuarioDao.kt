package com.example.ej4room.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ej4room.Entity.UsuarioEntity

@Dao
interface UsuarioDao {
    @Query("SELECT * FROM UsuarioEntity WHERE email = :email AND contrasena = :contrasena")
    suspend fun obtenerUsuario(email: String, contrasena: String): UsuarioEntity?

    @Insert
    suspend fun agregarUsuario(usuarioEntity: UsuarioEntity)

    @Update
    suspend fun actualizarUsuario(usuarioEntity: UsuarioEntity)

    @Delete
    suspend fun borrarUsuario(usuarioEntity: UsuarioEntity)
}
