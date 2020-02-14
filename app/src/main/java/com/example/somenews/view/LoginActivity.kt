package com.example.somenews.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.somenews.R
import com.example.somenews.viewmodel.UserViewModel
import com.example.somenews.db.UsersDataBase
import com.example.somenews.db.entity.User
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        sign_up_user_login_button.setOnClickListener {
            val name = registration_user_name_edit_text.text.toString()
            val password = registration_user_password_edit_text.text.toString()
             println(userViewModel.userSignUp(name,password))
        }

        clickOnRegistrationLink()
    }

    private fun clickOnRegistrationLink(){

        registration_textlink_textView.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }

}
