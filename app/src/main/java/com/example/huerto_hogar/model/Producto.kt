package com.example.huerto_hogar.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "producto")
data class Producto(
    @PrimaryKey (autoGenerate = true)
    val id: Int,
    val nombre: String,
    val precio: Double,
    val categoria: String, /*FRUTAS, VERDURAS, ORGÁNICOS*/
    val imagenURL: String,
    val descripcion: String,
    val unidadMedida: String,
    val stock: Boolean = true

)