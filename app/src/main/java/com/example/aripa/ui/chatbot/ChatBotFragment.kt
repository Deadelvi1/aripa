package com.example.aripa.ui.chatbot

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import io.noties.markwon.Markwon
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import com.google.gson.JsonParser
import java.io.IOException
import com.example.aripa.BuildConfig // Menggunakan BuildConfig dari proyek ARIPA
import com.example.aripa.R // Menggunakan R dari proyek ARIPA
import com.example.aripa.databinding.FragmentChatbotBinding // Menggunakan binding untuk fragment ini

class ChatbotFragment : Fragment() {

    private var _binding: FragmentChatbotBinding? = null
    // Properti ini hanya valid antara onCreateView dan onDestroyView.
    private val binding get() = _binding!!

    private lateinit var markwon: Markwon
    // Pastikan API_KEY sudah didefinisikan di BuildConfig proyek ARIPA Anda
    val API_KEY = BuildConfig.API_KEY
    private val GEMINI_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=$API_KEY"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Menggunakan View Binding untuk meng-inflate layout fragment
        _binding = FragmentChatbotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        markwon = Markwon.create(requireContext())

        // Mengakses View melalui objek binding
        val chatLayout: LinearLayout = binding.chatLayout
        val input: EditText = binding.inputMessage
        val sendButton: ImageButton = binding.sendButton
        val chatScroll: ScrollView = binding.chatScroll // Jika Anda ingin mengelola scroll otomatis

        sendButton.setOnClickListener {
            val userMessage = input.text.toString().trim()
            if (userMessage.isNotEmpty()) {
                input.setText("") // Kosongkan input setelah pesan dikirim
                addMessage(chatLayout, "Kamu: $userMessage", false) // Menambahkan pesan pengguna
                getGeminiResponse(userMessage) { response ->
                    // Pastikan pembaruan UI dilakukan di Main Thread
                    activity?.runOnUiThread {
                        addMessage(chatLayout, "Gemini: $response", true) // Menambahkan respons Gemini
                        // Optional: Scroll otomatis ke bawah
                        chatScroll.post { chatScroll.fullScroll(View.FOCUS_DOWN) }
                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Penting untuk membersihkan referensi binding
    }

    // Fungsi untuk menambahkan pesan ke tampilan chat
    private fun addMessage(layout: LinearLayout, message: String, isMarkdown: Boolean) {
        val textView = TextView(requireContext())

        if (isMarkdown) {
            markwon.setMarkdown(textView, message)
        } else {
            textView.text = message
        }

        textView.textSize = 16f
        textView.setPadding(8, 8, 8, 8)

        layout.addView(textView)
    }

    // Fungsi untuk mendapatkan respons dari Gemini API
    private fun getGeminiResponse(prompt: String, callback: (String) -> Unit) {
        val client = OkHttpClient()
        val json = """
        {
          "contents": [
            {
              "parts": [
                { "text": "$prompt" }
              ]
            }
          ]
        }
        """.trimIndent()

        val body = RequestBody.create("application/json".toMediaTypeOrNull(), json)
        val request = Request.Builder()
            .url(GEMINI_URL)
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback("Gagal terhubung: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()

                Log.d("GeminiResponse", "HTTP Code: ${response.code}")
                Log.d("GeminiResponse", "Body: $responseBody")

                if (response.isSuccessful && responseBody != null) {
                    try {
                        val json = JsonParser.parseString(responseBody).asJsonObject

                        if (json.has("candidates")) {
                            val rawText = json.getAsJsonArray("candidates")?.get(0)?.asJsonObject
                                ?.get("content")?.asJsonObject
                                ?.get("parts")?.asJsonArray?.get(0)?.asJsonObject
                                ?.get("text")?.asString ?: "Tidak ada respons dari parsing."
                            callback(rawText)
                        } else {
                            val promptFeedback = json.get("promptFeedback")?.toString() ?: "Tidak ada detail."
                            callback("Respons tidak mengandung 'candidates'. Kemungkinan diblokir. Feedback: $promptFeedback")
                        }

                    } catch (e: Exception) {
                        callback("Kesalahan parsing respons: ${e.message}")
                    }
                } else {
                    val errorCode = response.code
                    val errorBody = responseBody ?: "Body kosong"
                    Log.e("GeminiAPI", "Request Gagal: Code $errorCode, Body: $errorBody")
                    callback("Gagal mendapatkan respons dari server (Kode: $errorCode). Cek Logcat untuk detail.")
                }
            }
        })
    }
}