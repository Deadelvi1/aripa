package com.example.aripa.register // KOREKSI: Package name

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.aripa.R // KOREKSI: Import R
import com.example.aripa.db.User // KOREKSI: Import User
import com.example.aripa.login.Login // KOREKSI: Import Login
import com.example.aripa.viewmodel.UserViewModel // KOREKSI: Import UserViewModel
import com.example.aripa.ui.pageactivity.Page1Activity // Tambahkan import untuk Page1Activity

class Register : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val emailInput = findViewById<EditText>(R.id.inputEmail)
        val passwordInput = findViewById<EditText>(R.id.inputPassword)
        val registerButton = findViewById<Button>(R.id.registerButton)
        val tvLogin = findViewById<TextView>(R.id.tvMasukDiSini)

        registerButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val user = User(email = email, sandi = password)
                userViewModel.addUser(user)
                Toast.makeText(this, "Registrasi Berhasil!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Page1Activity::class.java) // KOREKSI: Arahkan ke Page1Activity
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Email dan Password Wajib Diisi!", Toast.LENGTH_SHORT).show()
            }
        }

        tvLogin.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            // Tidak finish() di sini agar bisa kembali dari Login ke Register
        }
    }
}