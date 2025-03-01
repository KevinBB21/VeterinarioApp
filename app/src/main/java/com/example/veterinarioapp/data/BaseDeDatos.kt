package com.example.veterinarioapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.veterinarioapp.Dao.*
import com.example.veterinarioapp.Entity.*

@Database(entities = [ReviewEntity::class, UsuarioEntity::class, FavoritoEntity::class], version = 1)
abstract class BaseDeDatos: RoomDatabase() {
    abstract fun reviewDao(): ReviewDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun favoritoDao(): FavoritoDao
}
