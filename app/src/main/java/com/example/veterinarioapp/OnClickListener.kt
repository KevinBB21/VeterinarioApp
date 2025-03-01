package com.example.veterinarioapp

import com.example.veterinarioapp.Entity.ReviewEntity

interface OnClickListener {
    fun alHacerClic(reviewEntity: ReviewEntity)

    fun alDarleAFavorito(reviewEntity: ReviewEntity)

    fun alEliminar(reviewEntity: ReviewEntity)
}
