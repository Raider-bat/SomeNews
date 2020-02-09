package com.example.somenews

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.somenews.usersdatabase.User
import com.example.somenews.usersdatabase.UsersDataBase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class LoginActivity : AppCompatActivity(), CoroutineScope {

    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        set_user_login_button.setOnClickListener {
            val name = user_name_edit_text.text.toString()
            val password = user_password_edit_text.text.toString()
            try {
                val user = checkByName(name) as User?
                if (user?.name == name){
                    Toast.makeText(this@LoginActivity,"Аккаунт с подобным именем уже существует",Toast.LENGTH_LONG).show()
                }else{
                    insertUser(User(name,password))
                }

            }catch (e:Exception){
                Toast.makeText(this@LoginActivity,e.toString(),Toast.LENGTH_LONG).show()
            }
        }

        find_user_login_button.setOnClickListener {
            val name = user_name_edit_text.text.toString()
            val password = user_password_edit_text.text.toString()
            try {
                val userName = (checkByName(name)) as User
                if (userName.name == name){
                    if (BCrypt.verifyer().verify(password.toCharArray(),userName.password).verified){
                        Toast.makeText(this@LoginActivity,"Вход",Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this@LoginActivity,"Неверный пароль",Toast.LENGTH_LONG).show()
                    }
                }
                   println(userName)
            }catch (e:Exception){
                Toast.makeText(this@LoginActivity,"Аккаунта с таким именем не существует",Toast.LENGTH_LONG).show()
            }
        }

        delete_button.setOnClickListener {
                val id = input_id.text.toString().toInt()
                val user = findById(id)
                deleteUser(user)

        }

        button_find_by_id.setOnClickListener {
            try {
                val id = input_id.text.toString().toInt()
                val user = findById(id)
                name_text_view.text = user.name
                password_text_view.text = user.password

            }catch (e:Exception){

                Toast.makeText(this@LoginActivity,e.cause.toString(),Toast.LENGTH_LONG).show()
            }
        }




    }

    private fun deleteUser(user: User) = launch{
        try {
            val userDao = UsersDataBase.getInstance(this@LoginActivity).userDao()
            userDao.delete(user)
            Toast.makeText(this@LoginActivity,"Успешно удален",Toast.LENGTH_LONG).show()

        }catch (e:Exception){
            Toast.makeText(this@LoginActivity,e.message,Toast.LENGTH_LONG).show()
        }
    }

    private fun checkByName(name: String) : User = runBlocking{
        val userDao = UsersDataBase.getInstance(this@LoginActivity).userDao()
        userDao.getByName(name)
    }

    private fun insertUser(user: User) = launch{
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

    private fun findById(id:Int):User = runBlocking {
            val userDao = UsersDataBase.getInstance(this@LoginActivity)
            userDao.userDao().getById(id)
    }

   private fun checkUserInDB(name: String, password: String): User = runBlocking {
        val userDao = UsersDataBase.getInstance(this@LoginActivity).userDao()
        userDao.checkUser(name,password)
    }
    override fun onDestroy() {
        super.onDestroy()
        coroutineContext.cancelChildren()
    }


}
