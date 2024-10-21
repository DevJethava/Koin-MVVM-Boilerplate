package com.devjethava.koinboilerplate.database.repository

import com.devjethava.koinboilerplate.database.dao.UserDao
import com.devjethava.koinboilerplate.database.entity.UserEntity

class UserRepository(private val userDao: UserDao) {

    fun insertDataAsync(userEntity: UserEntity) = userDao.insertUserData(userEntity)

    fun getListAsync() = userDao.fetchUsersData()
}