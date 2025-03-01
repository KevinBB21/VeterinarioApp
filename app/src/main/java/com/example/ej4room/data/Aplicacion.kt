package com.example.ej4room.data

import android.app.Application
import androidx.room.Room
import com.example.ej4room.Entity.NoticiaEntity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Aplicacion : Application() {
    companion object {
        lateinit var baseDeDatos: BaseDeDatos
    }

    override fun onCreate() {
        super.onCreate()

        baseDeDatos = Room.databaseBuilder(
            this,
            BaseDeDatos::class.java,
            "BaseDeDatos"
        )
            .fallbackToDestructiveMigration()
            .build()

        agregarNoticias()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun agregarNoticias() {
        // Se usa GlobalScope porque esta operación de inserción inicial de datos
        // no está ligada a ningún componente específico de la UI y debe ejecutarse
        // independientemente del ciclo de vida de una Activity.
        // Usar lifecycleScope aquí podría hacer que la operación se cancele si
        // la Activity se destruye antes de completarse.
        GlobalScope.launch(Dispatchers.IO) {
            baseDeDatos.noticiaDao().borrarTodasLasNoticias()
            baseDeDatos.noticiaDao().actualizarNoticias()

                listOf(
                    NoticiaEntity(
                        titulo = "Los incendios en California como arma política: un avance de cómo será Trump con sus adversarios políticos.",
                        descripcion = "Donald Trump, y sus aliados en las redes sociales, han desencadenado un enfrentamiento político entre demócratas y republicanos",
                        fecha = "2025-01-14",
                        esFavorita = false,
                        imagenUrl = "https://img2.rtve.es/i/?w=1600&i=01736848155439.jpg",
                        noticiaUrl = "https://www.rtve.es/noticias/20250114/incendios-california-arma-politica-trump-adversarios-politicos/16406076.shtml"
                    ),
                    NoticiaEntity(
                        titulo = "Israel y Hamás discuten en Catar los últimos detalles de un acuerdo de tregua en Gaza que está más cerca que nunca",
                        descripcion = "Discusiones sobre un alto el fuego en la Franja de Gaza que detenga los bombardeos y libere a los rehenes.",
                        fecha = "2025-01-12",
                        esFavorita = false,
                        imagenUrl = "https://img2.rtve.es/i/?w=1600&i=01736845842483.jpg",
                        noticiaUrl = "https://www.rtve.es/noticias/20250114/guerra-gaza-israel-hamas-tregua-negociaciones/16405926.shtml"
                    ),
                    NoticiaEntity(
                        titulo = "Feijóo avanza que apoyará la reforma de pensiones pactada por el Gobierno con la patronal y los sindicatos",
                        descripcion = "l presidente de PP, Alberto Núñez Feijóo, ha avanzado este martes que apoyará la reforma de pensiones",
                        fecha = "2025-01-09",
                        esFavorita = false,
                        imagenUrl = "https://img2.rtve.es/i/?w=1600&i=01736851112472.jpg",
                        noticiaUrl = "https://www.rtve.es/noticias/20250114/feijoo-pp-reforma-pensiones-gobierno/16406190.shtml"
                    ),
                    NoticiaEntity(
                        titulo = "La Policía detiene a Kike Salas, jugador del Sevilla, por presuntas apuestas ilegales.",
                        descripcion = "provocar que le sacaran tarjetas amarillas en partidos para que algunas personas de su entorno pudieran ganar dinero con apuestas deportivas. En siete de ocho partidos",
                        fecha = "2025-01-11",
                        esFavorita = false,
                        imagenUrl = "https://img2.rtve.es/i/?w=1600&i=01736861519547.jpg",
                        noticiaUrl = "https://www.rtve.es/deportes/20250114/kike-salas-detenido-forzar-tarjetas-amarillas-apuestas-ilegales/16406570.shtml"
                    ),
                    NoticiaEntity(
                        titulo = "el Supremo acusa al fiscal general sin prueba concreta y hace elucubraciones sobre Moncloa",
                        descripcion = "El Gobierno ha acusado este martes al Tribunal Supremo de acusar al fiscal general del Estado, Álvaro García Ortiz, sin tener prueba concreta",
                        fecha = "2025-01-08",
                        esFavorita = false,
                        imagenUrl = "https://img2.rtve.es/i/?w=1600&i=01736864447366.jpg",
                        noticiaUrl = "https://www.rtve.es/noticias/20250114/gobierno-supremo-acusa-fiscal-general-sin-pruebas-concretas-vincula-moncloa-basandose-elucubraciones/16406646.shtml"
                    ),
                    NoticiaEntity(
                        titulo = "El Gobierno nombra a Álvaro Fernández Heredia como nuevo presidente de Renfe",
                        descripcion = "El ministro de Transportes y Movilidad Sostenible, Óscar Puente, ha nombrado este martes a Álvaro Fernández Heredia como nuevo presidente de Renfe",
                        fecha = "2025-01-03",
                        esFavorita = false,
                        imagenUrl = "https://img2.rtve.es/i/?w=1600&i=01736852558524.jpg",
                        noticiaUrl = "https://www.rtve.es/noticias/20250114/gobierno-nombra-a-alvaro-fernandez-heredia-como-nuevo-presidente-renfe/16406115.shtml"
                    ),
                    NoticiaEntity(
                        titulo = "Buscar piso, una historia de terror: cómo me intentaron estafar siete veces en diez días",
                        descripcion = "Cuatro años después, me mudo por tercera vez, pero es la primera vez que los precios me expulsan del centro para encontrar un sitio donde vivir",
                        fecha = "2025-01-02",
                        esFavorita = false,
                        imagenUrl = "https://img2.rtve.es/i/?w=1600&i=01736353433277.jpg",
                        noticiaUrl = "https://www.rtve.es/noticias/20250112/buscar-piso-madrid-como-intentaron-estafar/16381102.shtml"
                    ),
                    NoticiaEntity(
                        titulo = "el bot social al hogar inteligente: la tecnología que cuida y acompaña a las personas mayores",
                        descripcion = "a menudo supone un reto para las familias que tratan de hacerlo posible. La tecnología se presenta como una importante aliada en la transformación del modelo de cuidados domiciliarios",
                        fecha = "2025-01-03",
                        esFavorita = false,
                        imagenUrl = "https://img2.rtve.es/i/?w=1600&i=01728562786731.jpg",
                        noticiaUrl = "https://www.rtve.es/noticias/20250114/bot-social-hogar-inteligente-tecnologia-cuida-acompana-personas-mayores/16282330.shtml"
                    ),
                    NoticiaEntity(
                        titulo = "La NASA lanza este miércoles la misión Blue Ghost hacia la luna",
                        descripcion = "a NASA y SpaceX tienen previsto el próximo miércoles el despegue desde Florida de la misión Blue Ghost 1",
                        fecha = "2024-12-27",
                        esFavorita = false,
                        imagenUrl = "https://img2.rtve.es/i/?w=1600&i=01736849810574.jpg",
                        noticiaUrl = "https://www.rtve.es/noticias/20250114/nasa-lanza-mision-blue-ghost-luna-base-humana-permanente/16406173.shtml"
                    ),
                    NoticiaEntity(
                        titulo = "Eventos astronómicos que no te puedes perder en 2025: un eclipse de Sol y dos de Luna",
                        descripcion = "en el que destacan cuatro eclipses, de los que tres podrán verse desde territorio español",
                        fecha = "2024-12-24",
                        esFavorita = false,
                        imagenUrl = "https://img2.rtve.es/i/?w=1600&i=01736518127422.jpg",
                        noticiaUrl = "https://www.rtve.es/noticias/20250113/eventos-astronomicos-no-puedes-perder-2025-eclipse-sol-dos-luna/16400387.shtml"
                    )
                ).forEach { noticia ->
                    baseDeDatos
                        .noticiaDao()
                        .agregarNoticia(noticia)
                }
            }
        }
    }

