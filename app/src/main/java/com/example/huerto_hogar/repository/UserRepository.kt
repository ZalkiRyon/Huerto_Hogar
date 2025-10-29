package com.example.huerto_hogar.repository

import com.example.huerto_hogar.data.UserDao
import com.example.huerto_hogar.data.UserEntity
import kotlinx.coroutines.flow.Flow

class UserRepository (private val userDao: UserDao){

    suspend fun addUser(entity: UserEntity){
        userDao.createUser(entity)
    }

    suspend fun deleteUser(entity: UserEntity){
        userDao.deleteUser(entity)
    }

    val listUsers: Flow<List<UserEntity>> = userDao.listUsers()

}