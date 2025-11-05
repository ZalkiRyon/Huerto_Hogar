package com.example.huerto_hogar.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.huerto_hogar.model.Producto

@Database(entities = [UserEntity::class, Producto::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun productoDao(): ProductoDao
}
