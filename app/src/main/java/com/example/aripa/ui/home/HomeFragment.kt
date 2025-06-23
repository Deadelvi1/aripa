package com.example.aripa.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button // Import Button jika btn_masuk adalah Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController // Import untuk findNavController
import com.example.aripa.R // Import R class untuk mengakses ID navigasi dan layout
import com.example.aripa.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // --- PENAMBAHAN KODE UNTUK btn_masuk DIMULAI DI SINI ---

        // Asumsi: Anda memiliki Button dengan ID 'btn_masuk' di fragment_home.xml
        // Jika View 'btn_masuk' bukan Button atau memiliki ID yang berbeda, sesuaikan di sini.
        // Anda mungkin perlu menambahkan android:id="@+id/btn_masuk" ke Button Anda di fragment_home.xml
        val btnMasuk: Button = binding.btnMasuk // Mengakses Button melalui View Binding

        btnMasuk.setOnClickListener {
            // Menggunakan Navigation Component untuk berpindah ke ChatbotFragment
            // R.id.nav_chatbot_fragment adalah ID tujuan (destination) di mobile_navigation.xml Anda.
            // Pastikan ID ini sesuai dengan yang Anda definisikan di NavGraph.
            findNavController().navigate(R.id.nav_chatbot_fragment)
        }

        // --- PENAMBAHAN KODE UNTUK btn_masuk BERAKHIR DI SINI ---
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}