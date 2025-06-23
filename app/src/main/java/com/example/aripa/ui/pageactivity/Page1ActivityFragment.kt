package com.example.aripa.ui.pageactivity

import com.example.aripa.R

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Page1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.fragment_page1_activity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets -> // KOREKSI: Mengubah target padding
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nextButton: Button = findViewById(R.id.buttonNextPage1)
        nextButton.setOnClickListener {
            val intent = Intent(this, Page2Activity::class.java)
            startActivity(intent)
            finish() // Tambahkan finish() agar tidak bisa kembali ke halaman ini
        }
    }
}