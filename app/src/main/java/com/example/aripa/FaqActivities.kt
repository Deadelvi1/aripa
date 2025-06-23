package com.example.aripa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class FaqActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq) // Ini akan memuat layout untuk FaqActivity

        // Jika Anda belum menggunakan FragmentContainerView di activity_faq.xml
        // dan ingin menambahkannya secara programatis:
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_for_faq, FaqFragment()) // Pastikan ID ini ada di activity_faq.xml
                .commit()
        }
    }
}