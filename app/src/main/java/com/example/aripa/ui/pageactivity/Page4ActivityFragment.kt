package com.example.aripa.ui.pageactivity // Pastikan ini sesuai dengan package Anda
import com.example.aripa.R
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// KOREKSI INI: Import MainActivity utama aplikasi Anda, yang berada di package root
import com.example.aripa.MainActivity // Hapus import yang salah, tambahkan import ini


class Page4Activity : AppCompatActivity() { // Pastikan nama kelas diawali huruf kapital: Page4Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // KOREKSI: Pastikan nama layout XML yang Anda gunakan di sini benar
        // Jika file layout Anda bernama activity_page4.xml, gunakan R.layout.activity_page4
        setContentView(R.layout.fragment_page4_activity) // KOREKSI INI: Masih memanggil fragment layout di Activity

        // KOREKSI: Arahkan ViewCompat.setOnApplyWindowInsetsListener ke root content view
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets -> // KOREKSI INI: Mengubah target padding
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val agreeCheckBox: CheckBox = findViewById(R.id.agreeCheckBox)
        val startMainButton: Button = findViewById(R.id.buttonStartMain)

        startMainButton.isEnabled = agreeCheckBox.isChecked

        agreeCheckBox.setOnCheckedChangeListener { _, isChecked ->
            startMainButton.isEnabled = isChecked
        }

        startMainButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java) // KOREKSI: Sekarang mengarah ke MainActivity yang benar
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }
    }
}