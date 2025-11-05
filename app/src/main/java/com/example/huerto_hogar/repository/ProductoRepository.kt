package com.example.huerto_hogar.repository

import com.example.huerto_hogar.data.ProductoDao
import com.example.huerto_hogar.model.Producto
import kotlinx.coroutines.flow.Flow

class ProductoRepository(val productoDao: ProductoDao) {

    suspend fun agregar(producto: Producto){
        productoDao.agregar(producto)
    }

    suspend fun eliminar(producto: Producto){
        productoDao.eliminar(producto)
    }

    val lerr: Flow<List<Producto>> = productoDao.leer()
}