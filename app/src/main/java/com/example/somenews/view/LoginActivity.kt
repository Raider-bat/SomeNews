package com.example.somenews.view

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.somenews.R
import com.example.somenews.db.UsersDataBase
import com.example.somenews.db.entity.User
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()


  /*      set_user_login_button.setOnClickListener {
            val name = user_name_edit_text.text.toString()
            val password = user_password_edit_text.text.toString()
            lifecycleScope.launch {
                try {
                    val user = checkByName(name) as User?
                    if (user?.name == name){
                        Toast.makeText(this@LoginActivity,"Аккаунт с подобным именем уже существует",Toast.LENGTH_LONG).show()
                    }else{
                        insertUser(
                            User(
                                name,
                                password
                            )
                        )
                    }

                }catch (e:Exception){
                    Toast.makeText(this@LoginActivity,e.toString(),Toast.LENGTH_LONG).show()
                }
            }
        }
   */

        sign_up_user_login_button.setOnClickListener {
            val name = user_name_edit_text.text.toString()
            val password = user_password_edit_text.text.toString()
            lifecycleScope.launch {
                try {
                    val userName =
                        withContext(Dispatchers.IO) {
                            (checkByName(
                                name
                            ))
                        }
                    val verified =
                        withContext(Dispatchers.IO) {
                            BCrypt
                                .verifyer()
                                .verify(password.toCharArray(), userName.password).verified
                        }
                    if (userName.name == name){
                        if (verified){
                            Toast.makeText(this@LoginActivity,"Вход",Toast.LENGTH_LONG).show()
                            if (userName.name == "Raider"){
                                val intent = Intent(this@LoginActivity,FeedAdminActivity::class.java)
                                startActivity(intent)
                            }else{
                                val intent = Intent(this@LoginActivity,FeedActivity::class.java)
                                startActivity(intent)
                            }
                        }else{
                            Toast.makeText(this@LoginActivity,"Неверный пароль",Toast.LENGTH_LONG).show()
                        }
                    }
                    println(userName)
                }catch (e:Exception){
                    Toast.makeText(this@LoginActivity,"Аккаунта с таким именем не существует",Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    private suspend fun checkByName(name: String) : User {
        val userDao = UsersDataBase.getInstance(this@LoginActivity).userDao()
        return userDao.getByName(name)
    }

    private fun insertUser(user: User) = lifecycleScope.launch{
        try {
            val userDao = UsersDataBase.getInstance(this@LoginActivity)
            val hashPassword = BCrypt.withDefaults().hashToString(12,user.password.toCharArray())
            user.password = hashPassword

             userDao.userDao().insert(user)
            Toast.makeText(this@LoginActivity,"Успешно",Toast.LENGTH_SHORT).show()
        } catch (e:Exception){
            println(e)
            Toast.makeText(this@LoginActivity,"Ошибка",Toast.LENGTH_SHORT).show()
        }
    }

}
