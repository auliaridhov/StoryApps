package com.harvdev.storyapp.ui.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController

fun Fragment.safeNavigate(
    currentDestinationId: Int,
    targetDestinationId: Int,
    args: Bundle? = null,
    navOptions: NavOptions? = null,
    extras: Navigator.Extras? = null
) {
    safeFindNavController()?.let {
        if (it.currentDestination?.id == currentDestinationId) {
            it.navigate(targetDestinationId, args, navOptions, extras)
        }
    }
}
fun Fragment.globalSafeNavigate(targetDestinationId: Int, args: Bundle? = null) {
    safeFindNavController()?.let {
        it.navigate(targetDestinationId, args)
    }
}
/*
 * can be used to replace regular popbackstack without worrying illegal state exception fragment not in fragment manager
 */
fun Fragment.safePopBackstack() {
    safeFindNavController()?.popBackStack()
}
fun Fragment.safePopBackStack(destinationId: Int, inclusive: Boolean) {
    safeFindNavController()?.popBackStack(destinationId, inclusive)
}
/*
 * replace regular findNavController , will return null when current fragment is not in fragment manager
 * instead of crashing with IllegalStateException
 */
fun Fragment.safeFindNavController(): NavController? {
    val navController: NavController?
    try {
        navController = findNavController(this)
    } catch (notFound: IllegalStateException) {
        return null
    }
    return navController
}
