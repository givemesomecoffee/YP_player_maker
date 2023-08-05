package givemesomecoffee.ru.playlistmaker.feature.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.view.iterator
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import givemesomecoffee.ru.playlistmaker.R
import givemesomecoffee.ru.playlistmaker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main), NavBarsController {

    private val binding: ActivityMainBinding by viewBinding()
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.bottom_nav)
            .setupWithNavController(navController)
        val startDestinations = buildList {
            findViewById<BottomNavigationView>(R.id.bottom_nav).menu.iterator().forEach {
                add(it.itemId)
            }
        }.toSet()
        setupActionBarWithNavController(navController, AppBarConfiguration(startDestinations))
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            hideBottomNavigation(false)
            setFullScreen(false)
        }
    }

    override fun hideBottomNavigation(hide: Boolean) {
        binding.bottomNav.isVisible = !hide
    }

    override fun setFullScreen(isFullScreen: Boolean) {
        hideBottomNavigation(isFullScreen)
        hideToolbar(isFullScreen)
    }

    override fun hideToolbar(hide: Boolean) {
        binding.toolbar.isVisible = !hide
    }
}