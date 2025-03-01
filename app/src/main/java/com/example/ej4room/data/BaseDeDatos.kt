package com.example.ej4room.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ej4room.Dao.*
import com.example.ej4room.Entity.*

@Database(entities = [NoticiaEntity::class, UsuarioEntity::class, FavoritoEntity::class], version = 1)
abstract class BaseDeDatos: RoomDatabase() {
    abstract fun noticiaDao(): NoticiaDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun favoritoDao(): FavoritoDao
}
