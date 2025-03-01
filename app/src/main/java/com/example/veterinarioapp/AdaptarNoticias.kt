package com.example.veterinarioapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.veterinarioapp.Entity.ReviewEntity
import com.example.veterinarioapp.databinding.ItemNewBinding

class AdaptarReviews(
    private var listaReviews: MutableList<ReviewEntity>,
    private var listener: OnClickListener
) : RecyclerView.Adapter<AdaptarReviews.ViewHolder>() {

    private lateinit var contexto: Context

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val binding = ItemNewBinding.bind(view)

        fun establecerListener(review: ReviewEntity) {
            with(binding) {
                root.setOnClickListener { listener.alHacerClic(review) }

                btnLike.setOnClickListener { listener.alDarleAFavorito(review) }

                root.setOnLongClickListener {
                    listener.alEliminar(review)
                    true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        contexto = parent.context
        val vista = LayoutInflater.from(contexto).inflate(R.layout.item_new, parent, false)
        return ViewHolder(vista)
    }

    override fun getItemCount(): Int = listaReviews.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val review = listaReviews[position]

        with(holder) {
            establecerListener(review)

            with(binding) {
                tvTitle.text = review.titulo
                tvDescription.text = review.descripcion
                tvDate.text = review.fecha

                val icono = if (review.esFavorita) R.drawable.ic_like_star else R.drawable.ic_unlike_star
                btnLike.setImageResource(icono)

                btnOpenLink.setOnClickListener {
                    val url = review.reviewUrl
                    try {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        contexto.startActivity(intent)
                    } catch (e: Exception) {
                        Toast.makeText(contexto, "No se pudo abrir el enlace", Toast.LENGTH_SHORT).show()
                    }
                }

                Glide.with(binding.tvImage.context)
                    .load(review.imagenUrl)
                    .error(R.drawable.ic_launcher_background)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(binding.tvImage)
            }
        }
    }

    fun agregar(review: ReviewEntity) {
        listaReviews.add(review)
        notifyDataSetChanged()
    }

    fun establecerReviews(reviews: MutableList<ReviewEntity>) {
        this.listaReviews = reviews
        notifyDataSetChanged()
    }

    fun actualizar(review: ReviewEntity) {
        val indice = listaReviews.indexOfFirst { it.id == review.id }

        if (indice != -1) {
            listaReviews[indice] = review
            notifyItemChanged(indice)
        }
    }

    fun eliminar(review: ReviewEntity) {
        val indice = listaReviews.indexOf(review)

        if (indice != -1) {
            listaReviews.removeAt(indice)
            notifyItemRemoved(indice)
        }
    }
}
