package com.example.aripa

import android.os.Bundle
import android.view.Menu
import androidx.navigation.ui.NavigationUI
import androidx.navigation.navOptions
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.aripa.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_fmipa_info, R.id.nav_app_info,
                R.id.nav_faq, R.id.nav_chatbot_fragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        // --- PENAMBAHAN KODE UNTUK MENANGANI KLIK ITEM DRAWER SECARA EKSPLISIT ---
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    // Ketika item "Beranda" diklik, navigasi ke sana dan pop back stack
                    // sampai ke root NavGraph (mobile_navigation) secara inklusif.
                    navController.navigate(
                        R.id.nav_home,
                        null, // Tidak ada argumen
                        androidx.navigation.navOptions {
                            popUpTo(R.id.mobile_navigation) {
                                inclusive = true // Hapus semua Fragment di back stack
                            }
                            launchSingleTop = true // Pastikan hanya satu instance HomeFragment
                        }
                    )
                    drawerLayout.closeDrawer(navView) // Tutup drawer setelah navigasi
                    true // Menandakan event ditangani
                }
                else -> {
                    // Untuk item lainnya, biarkan NavController menanganinya secara default
                    NavigationUI.onNavDestinationSelected(menuItem, navController)
                    drawerLayout.closeDrawer(navView) // Tutup drawer setelah navigasi
                    true // Menandakan event ditangani
                }
            }
        }
        // --- AKHIR PENAMBAHAN KODE ---
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}