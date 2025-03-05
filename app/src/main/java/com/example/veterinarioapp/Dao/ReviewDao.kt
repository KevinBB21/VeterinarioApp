package com.example.veterinarioapp.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.veterinarioapp.Entity.ReviewEntity

@Dao
interface ReviewDao {
    @Query("SELECT * FROM ReviewEntity")
    suspend fun obtenerTodasLasReviews(): MutableList<ReviewEntity>
    @Insert
    suspend fun agregarReview(reviewEntity: ReviewEntity)
    @Update
    suspend fun actualizarReview(reviewEntity: ReviewEntity)
    @Delete
    suspend fun borrarReview(reviewEntity: ReviewEntity)
    @Query("DELETE FROM ReviewEntity")
    suspend fun borrarTodasLasReviews()
    @Query("UPDATE ReviewEntity SET id = 0")
    suspend fun actualizarReviews()
}
