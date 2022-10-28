package com.devjethava.koinboilerplate.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devjethava.koinboilerplate.database.entity.UserEntity

@Dao
interface UserDao {

    /**
     * Save User Data UserEntity
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserData(userEntity: UserEntity)

    /**
     * Get all the data of UserEntity
     */
    @Query("SELECT * FROM User")
    suspend fun fetchUsersData(): List<UserEntity>
}