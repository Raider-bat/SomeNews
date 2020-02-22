package com.example.somenews.view

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.somenews.R
import com.example.somenews.enumclass.EnumAuthResult
import com.example.somenews.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_registration.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class RegistrationActivity : AppCompatActivity() {

    private val userViewModel : UserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        supportActionBar?.hide()

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

                    toast(EnumAuthResult.ACCOUNT_CREATE_SUCCESSFUL.string)
                    finish()
                }

                EnumAuthResult.ACCOUNT_CREATE_ALREADY_EXIST ->{

                    toast(EnumAuthResult.ACCOUNT_CREATE_ALREADY_EXIST.string)
                }

                EnumAuthResult.ACCOUNT_CREATE_EXCEPTION ->{

                    toast(EnumAuthResult.ACCOUNT_CREATE_EXCEPTION.string)
                }

                EnumAuthResult.WRONG_DATA ->{

                    toast(EnumAuthResult.WRONG_DATA.string)
                }

                else -> {
                    toast("Неизвестная ошибка")
                    Timber.d(result.string)
                }
            }
        })
    }

    private fun Context.toast(message: CharSequence, duration: Int  = Toast.LENGTH_SHORT ){
        Toast.makeText(this, message,duration).show()
    }
}
