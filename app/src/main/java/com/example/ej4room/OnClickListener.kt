package com.example.ej4room

import com.example.ej4room.Entity.NoticiaEntity

interface OnClickListener {
    fun alHacerClic(noticiaEntity: NoticiaEntity)

    fun alDarleAFavorito(noticiaEntity: NoticiaEntity)

    fun alEliminar(noticiaEntity: NoticiaEntity)
}
