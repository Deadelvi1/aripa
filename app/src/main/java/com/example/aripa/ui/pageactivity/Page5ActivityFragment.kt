package com.example.aripa.ui.pageactivity

import com.example.aripa.R
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox // <--- Tambahkan import ini untuk CheckBox
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// Import MainActivity secara eksplisit karena berada di package yang berbeda
import com.example.aripa.MainActivity


class Page5Activity : AppCompatActivity() { // <--- Pastikan nama kelas diawali huruf kapital: Page4Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // KOREKSI: Pastikan nama layout XML yang Anda gunakan di sini benar
        // Jika file layout Anda bernama activity_page4.xml, gunakan R.layout.activity_page4
        setContentView(R.layout.fragment_page4_activity) // <--- KOREKSI INI

        // KOREKSI: Arahkan ViewCompat.setOnApplyWindowInsetsListener ke root ScrollView
        // Ini adalah ID root ScrollView yang sudah kita definisikan di activity_page4.xml
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.textViewPage4)) { v, insets -> // <--- KOREKSI INI
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Dapatkan referensi ke CheckBox dan Button
        val agreeCheckBox: CheckBox = findViewById(R.id.agreeCheckBox)
        val startMainButton: Button = findViewById(R.id.buttonStartMain) // ID tombol di XML harus "buttonStartMain"

        // Atur status awal tombol: tidak aktif secara default (karena enabled="false" di XML)
        startMainButton.isEnabled = agreeCheckBox.isChecked

        // Tambahkan listener untuk CheckBox
        agreeCheckBox.setOnCheckedChangeListener { _, isChecked ->
            // Tombol "Mulai" hanya akan aktif jika checkbox dicentang
            startMainButton.isEnabled = isChecked
        }

        // Listener untuk tombol "Mulai"
        startMainButton.setOnClickListener {
            // Karena ini Halaman 4 dengan tombol "Mulai" dan checkbox persetujuan,
            // ini adalah akhir dari flow onboarding dan akan langsung ke MainActivity.
            val intent = Intent(this, MainActivity::class.java)

            // Flags ini penting untuk membersihkan back stack
            // Agar ketika MainActivity terbuka, tombol back akan keluar dari aplikasi
            // bukan kembali ke halaman onboarding.
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

            startActivity(intent)
            finish() // Menutup Page4Activity agar tidak bisa kembali dengan tombol back
        }
    }
}