package br.com.carlosscotus.npbrasil.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import br.com.carlosscotus.npbrasil.R

open class BaseFragment : Fragment() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.gamesFragment,
                R.id.searchFragment,
                R.id.favoritesFragment
            )
        )
    }

    fun setupToolbarNavigation(toolbar: Toolbar) {
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)

        toolbar.setupWithNavController(findNavController(), appBarConfiguration)
        findNavController().addOnDestinationChangedListener { _, destination, _ ->
            val topDestination = appBarConfiguration.topLevelDestinations.contains(destination.id)
            if(!topDestination) {
                toolbar.setNavigationIcon(R.drawable.ic_round_arrow_back_ios_new_24)
            }
        }
    }
}