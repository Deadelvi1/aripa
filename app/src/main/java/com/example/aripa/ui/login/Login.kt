package com.example.aripa.login // KOREKSI: Package name

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.aripa.MainActivity // KOREKSI: Import MainActivity
import com.example.aripa.R // KOREKSI: Import R
import com.example.aripa.register.Register // KOREKSI: Import Register
import com.example.aripa.viewmodel.UserViewModel // KOREKSI: Import UserViewModel
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val emailInput = findViewById<EditText>(R.id.etEmail)
        val passwordInput = findViewById<EditText>(R.id.etPassword)
        val loginButton = findViewById<Button>(R.id.btnLogin)
        val tvDaftar = findViewById<TextView>(R.id.tvDaftarDiSini)

        loginButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                lifecycleScope.launch {
                    val user = userViewModel.findUserByEmailAndPassword(email, password)
                    if (user != null) {
                        Toast.makeText(this@Login, "Login Berhasil!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@Login, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@Login, "Email atau Password Salah!", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Email dan Password Wajib Diisi!", Toast.LENGTH_SHORT).show()
            }
        }

        tvDaftar.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
            // Tidak finish() di sini agar bisa kembali dari Register ke Login
        }
    }
}