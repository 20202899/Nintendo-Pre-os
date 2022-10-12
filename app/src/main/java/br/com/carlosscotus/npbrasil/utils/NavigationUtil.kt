package br.com.carlosscotus.npbrasil.utils

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator

fun NavController.safeNavigate(
    directions: NavDirections,
    fragmentNavigatorExtras: FragmentNavigator.Extras
) = try {
    this.navigate(directions, fragmentNavigatorExtras)
} catch (e: NullPointerException) {} catch (e: IllegalArgumentException) {}