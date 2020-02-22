package com.example.somenews.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.somenews.R
import com.example.somenews.enumclass.EnumAuthResult
import com.example.somenews.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.viewmodel.ext.android.viewModel


class LoginActivity : AppCompatActivity() {


    private val myViewModel : UserViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        val verifiedUserLiveData = myViewModel.getVerifiedUserLiveData()

        verifiedUserLiveData.observe(this, Observer { verifiedUser ->
         if (verifiedUser?.name == "Raider"){

             val intent = Intent(this, FeedAdminActivity::class.java)
             startActivity(intent)
             finish()

         } else
             if (verifiedUser?.name != "Raider" && verifiedUser?.name !=null){

             val intent = Intent(this, FeedActivity::class.java)
             startActivity(intent)
             finish()
         }

        } )

        sign_in_user_login_button.setOnClickListener {

            val name = registration_user_name_edit_text.text.toString()
            val password = registration_user_password_edit_text.text.toString()
            myViewModel.userSignUp(name,password)
        }

        signUpResultObserver()
        clickOnRegistrationLink()
    }

    private fun signUpResultObserver() {
        val signInResult = myViewModel.getSignInResult()

        signInResult.observe(this, Observer<EnumAuthResult> { result ->
            when(result){

                EnumAuthResult.ADMIN_SIGN_UP ->{

                    toast(EnumAuthResult.ADMIN_SIGN_UP.string)

                    val intent = Intent(this, FeedAdminActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                EnumAuthResult.USER_SIGN_UP ->{

                    toast(EnumAuthResult.USER_SIGN_UP.string)

                    val intent = Intent(this, FeedActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                EnumAuthResult.WRONG_PASSWORD ->{

                    toast(EnumAuthResult.WRONG_PASSWORD.string)
                }

                EnumAuthResult.ACCOUNT_NOT_FOUND ->{

                    toast(EnumAuthResult.ACCOUNT_NOT_FOUND.string)
                }

                EnumAuthResult.WRONG_DATA ->{

                    toast(EnumAuthResult.WRONG_DATA.string)
                }

                else ->  toast("Неизвестная ошибка")
            }
        })
    }

    private fun clickOnRegistrationLink(){

        registration_textlink_textView.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }


    private fun Context.toast(message: CharSequence, duration: Int  = Toast.LENGTH_SHORT ){
        Toast.makeText(this, message,duration).show()
    }
}
