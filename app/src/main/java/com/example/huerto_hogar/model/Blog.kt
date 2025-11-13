package com.example.huerto_hogar.model

import com.example.huerto_hogar.R

data class BlogPost(
    val id: String,
    val titulo: String,
    val contenido: String,
    val autor: String,
    val fecha: String,
    val categoria: String,
    val imagenUrl: Int? = null
)

object BlogRepository {
    fun getBlogs(): List<BlogPost> {
        return listOf(
            BlogPost(
                id = "1",
                titulo = "Introducción",
                contenido = "Jetpack Compose...",
                autor = "Perico Palore",
                fecha = "15 - marzo 2020",
                categoria = "Verduras",
                imagenUrl = R.drawable.kale
            ),
            BlogPost(
                id = "2",
                titulo = "Perico Palote",
                contenido = "Perico pelote",
                autor = "Pepa la cerda",
                fecha = "11 - marzo - 2020",
                categoria = "Frutas",
                imagenUrl = R.drawable.kale
            )
        )
    }

    fun getBlogById(id: String): BlogPost? {
        return getBlogs().find { it.id == id }
    }
}
