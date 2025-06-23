package com.example.aripa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText

class FaqFragment : Fragment() {

    private lateinit var faqRecyclerView: RecyclerView
    private lateinit var searchEditText: TextInputEditText
    private lateinit var faqAdapter: FaqAdapter
    private var allFaqItems: List<FaqItem> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_faq, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        faqRecyclerView = view.findViewById(R.id.faqRecyclerView)
        searchEditText = view.findViewById(R.id.searchEditText)

        // Initialize with dummy data (replace with real data fetching)
        allFaqItems = getDummyFaqData()

        faqAdapter = FaqAdapter(allFaqItems)
        faqRecyclerView.layoutManager = LinearLayoutManager(context)
        faqRecyclerView.adapter = faqAdapter

        // Implement search functionality (optional but good for interactive FAQ)
        searchEditText.setOnEditorActionListener { v, actionId, event ->
            // You can add search logic here, e.g., filtering the faqAdapter
            // For now, let's just clear focus
            searchEditText.clearFocus()
            false
        }
        searchEditText.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterFaqItems(s.toString())
            }

            override fun afterTextChanged(s: android.text.Editable?) {}
        })
    }

    private fun filterFaqItems(query: String) {
        val filteredList = if (query.isBlank()) {
            allFaqItems
        } else {
            allFaqItems.filter {
                it.question.contains(query, ignoreCase = true) ||
                        it.answer.contains(query, ignoreCase = true)
            }
        }
        // Create a new adapter instance with the filtered list to reflect changes
        faqAdapter = FaqAdapter(filteredList)
        faqRecyclerView.adapter = faqAdapter
    }

    private fun getDummyFaqData(): List<FaqItem> {
        // In a real app, you would fetch this data from a backend or local database
        return listOf(
            FaqItem("1", "Apa itu ARIPA?", "ARIPA (Asisten Riset dan Informasi Terpadu FMIPA) adalah aplikasi mobile dari Universitas Lampung yang dirancang untuk membantu mahasiswa dan dosen."),
            FaqItem("2", "Siapa yang bisa menggunakan ARIPA?", "Aplikasi ini dapat digunakan oleh mahasiswa, dosen, dan tenaga kependidikan di lingkungan FMIPA Universitas Lampung."),
            FaqItem("3", "Apa saja fitur utama di ARIPA?", "Chatbot interaktif untuk menjawab pertanyaan akademik, Layanan administrasi praktis."),
            FaqItem("4", "Apakah data saya aman di ARIPA?", "Kami mematuhi kebijakan privasi dan perlindungan data pengguna."),
            FaqItem("5", "Bagaimana cara melaporkan bug atau masalah?", "Anda dapat melaporkan bug atau masalah melalui fitur 'Laporkan Masalah' di dalam aplikasi atau menghubungi tim dukungan kami.")
        )
    }
}