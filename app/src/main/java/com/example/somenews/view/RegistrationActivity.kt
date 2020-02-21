package com.example.somenews.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.somenews.R
import com.example.somenews.enumclass.EnumAuthResult
import com.example.somenews.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_registration.*
import org.koin.android.viewmodel.ext.android.viewModel

class RegistrationActivity : AppCompatActivity() {

    private val userViewModel : UserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        registration_user_button.setOnClickListener {
            val name = registration_user_name_edit_text.text.toString()
            val password = registration_user_password_edit_text.text.toString()
            userViewModel.userRegistration(name, password)

        }
        resultCreateAccount()
    }

    private fun resultCreateAccount() {
        val signUpResult = userViewModel.getSignUpResult()

        signUpResult.observe(this,  Observer<EnumAuthResult>{ result ->
            when(result){
                EnumAuthResult.ACCOUNT_CREATE_SUCCESSFUL ->{
                    Toast.makeText(this,"Успешно", Toast.LENGTH_SHORT).show()
                    finish()
                }

                EnumAuthResult.ACCOUNT_CREATE_ALREADY_EXIST ->{
                    Toast.makeText(this,"Подобный аккаунт уже существует", Toast.LENGTH_SHORT).show()
                }

                EnumAuthResult.ACCOUNT_CREATE_EXCEPTION ->{
                    Toast.makeText(this,"Ошибка регистрации", Toast.LENGTH_SHORT).show()
                }

                EnumAuthResult.WRONG_DATA ->{
                    Toast.makeText(this,"Неверно ведённые данные", Toast.LENGTH_SHORT).show()
                }

                else -> Toast.makeText(this,"Неизвесная ошибка", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
