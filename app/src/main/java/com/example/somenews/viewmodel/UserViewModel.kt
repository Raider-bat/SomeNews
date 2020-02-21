package com.example.somenews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.somenews.db.entity.User
import com.example.somenews.db.entity.VerifiedUser
import com.example.somenews.enumclass.EnumAuthResult
import com.example.somenews.repository.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(private val repository: UsersRepository): ViewModel() {

    private var signInResult = MutableLiveData<EnumAuthResult>()
    private var signUpResult = MutableLiveData<EnumAuthResult>()
    private var verifiedUserResult = MutableLiveData<VerifiedUser>()

    fun getSignInResult(): LiveData<EnumAuthResult> = signInResult
    fun getSignUpResult(): LiveData<EnumAuthResult> = signUpResult

    fun getVerifiedUserLiveData() : LiveData<VerifiedUser?> {

        getVerifiedUser()
        return verifiedUserResult
    }

    fun deleteVerifiedUser() = viewModelScope.launch {
        repository.deleteVerifiedUser()
    }

    fun userSignUp(name:String, password:String){
         viewModelScope.launch {
            try {
                if (name.length in 3..25 || password.length in 6..30 ) {
                    val user = repository.getByName(name)
                    println(user)
                    val verified =
                        withContext(Dispatchers.IO) {
                            BCrypt
                                .verifyer()
                                .verify(password.toCharArray(), user.password).verified
                        }
                    if (user.name == name) {

                        if (verified) {

                            if (user.name == "Raider") {
                                signInResult.value = EnumAuthResult.ADMIN_SIGN_UP

                            } else {
                                signInResult.value = EnumAuthResult.USER_SIGN_UP
                            }
                            repository.insertVerifiedUser(VerifiedUser(user.name, user.password))

                        } else {
                            signInResult.value = EnumAuthResult.WRONG_PASSWORD
                        }
                    }
                }else{
                    signInResult.value = EnumAuthResult.WRONG_DATA
                }
            } catch (e: Exception) {
                signInResult.value = EnumAuthResult.ACCOUNT_NOT_FOUND
                println(e)
            }
         }

    }

    fun userRegistration(name: String,password: String) = viewModelScope.launch{
        try {

            if (name.length in 3..25 || password.length in 6..30 ) {
                val user = repository.getByName(name) as User?

                if (user?.name != null) {

                    signUpResult.postValue(EnumAuthResult.ACCOUNT_CREATE_ALREADY_EXIST)
                } else {

                    val hashPassword = BCrypt.withDefaults()
                        .hashToString(
                            12,
                            password.toCharArray()
                        )
                    repository.insert(User(name, hashPassword))
                    signUpResult.postValue(EnumAuthResult.ACCOUNT_CREATE_SUCCESSFUL)
                }
            } else{
                signUpResult.postValue(EnumAuthResult.WRONG_DATA)
            }
        }catch (e:Exception){

            signUpResult.postValue(EnumAuthResult.ACCOUNT_CREATE_EXCEPTION)
       }
    }

    private fun getVerifiedUser() = viewModelScope.launch {

        verifiedUserResult.value = repository.getVerifiedUser()
    }
}