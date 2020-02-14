package com.example.somenews.viewmodel

import android.app.Application
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.somenews.db.entity.User
import com.example.somenews.repository.UsersRepository
import com.example.somenews.view.FeedActivity
import com.example.somenews.view.FeedAdminActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(application: Application): AndroidViewModel(application) {
    private val context = application.baseContext
    private val repository = UsersRepository(application)

    fun userSignUp(name:String, password:String){
         viewModelScope.launch {
            try {
                val user = repository.getByName(name)
                val verified =
                    withContext(Dispatchers.IO) {
                        BCrypt
                            .verifyer()
                            .verify(password.toCharArray(), user.password).verified
                    }
                if (user.name == name) {
                    if (verified) {
                        Toast.makeText(context, "Вход", Toast.LENGTH_LONG).show()
                        if (user.name == "Raider") {
                            val intent = Intent(context, FeedAdminActivity::class.java)
                            context.startActivity(intent)

                        } else {
                            val intent = Intent(context, FeedActivity::class.java)
                            context.startActivity(intent)
                        }
                    } else {
                        Toast.makeText(context, "Неверный пароль", Toast.LENGTH_LONG).show()

                    }
                }
            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    "Аккаунта с таким именем не существует",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        }
    }

    fun userRegistration(name: String,password: String) = viewModelScope.launch{
        try {
            val user = repository.getByName(name) as User?

            if (user?.name != null){
                Toast.makeText(context,
                    "Аккаунт с подобным именем уже существует",
                    Toast.LENGTH_LONG)
                    .show()
            }else{
                val hashPassword = BCrypt.withDefaults()
                    .hashToString(12,
                    password.toCharArray())

                repository.insert(User(name, hashPassword))
                Toast.makeText(context,"Успешно",Toast.LENGTH_SHORT).show()
                val intent = Intent(context, FeedActivity::class.java)
                context.startActivity(intent)
            }
        }catch (e:Exception){
            Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show()
        }
    }
}