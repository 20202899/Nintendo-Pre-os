package br.com.carlosscotus.npbrasil.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import br.com.carlosscotus.npbrasil.R
import br.com.carlosscotus.npbrasil.databinding.ActivityMainBinding
import com.google.android.material.color.DynamicColors
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_main) as NavHostFragment
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.gamesFragment,
                R.id.searchFragment
            )
        )

        binding.toolbar.setupWithNavController(navHostFragment.navController, appBarConfiguration)
        binding.bottomNavigation.setupWithNavController(navHostFragment.navController)

        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            val topDestination = appBarConfiguration.topLevelDestinations.contains(destination.id)
            if(!topDestination) {
                binding.toolbar.setNavigationIcon(R.drawable.ic_round_arrow_back_ios_new_24)
            }
        }

        DynamicColors.applyToActivitiesIfAvailable(application)
    }
}