package com.example.somenews.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.somenews.R
import com.example.somenews.db.UsersDataBase
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
    }


}
