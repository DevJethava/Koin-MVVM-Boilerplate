package com.devjethava.koinboilerplate.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devjethava.koinboilerplate.database.entity.UserEntity
import com.devjethava.koinboilerplate.database.repository.UserRepository
import com.devjethava.koinboilerplate.helper.Preference
import com.devjethava.koinboilerplate.model.repository.ApiRepository
import kotlinx.coroutines.launch

class UserViewModel(
    private val userRepository: UserRepository,
    preference: Preference
) : BaseViewModel(preference) {
    private lateinit var userData: MutableLiveData<List<UserEntity>>

    fun addUserData(userEntity: UserEntity) {
        ioScope.launch { userRepository.insertDataAsync(userEntity) }
    }

    fun getAllUserList(): LiveData<List<UserEntity>> {
        userData = MutableLiveData()
        ioScope.launch { userData.postValue(userRepository.getListAsync()) }
        return userData
    }
}