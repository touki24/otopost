package com.touki.otopost.util.extension

import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator

/** Created by mentok on 9/18/21 */

/**
 * Handling for:
 * Fatal Exception: java.lang.IllegalArgumentException
 * navigation destination com.xxx.yyy:id/action_aFragment_to_bFragment is unknown to this NavController
 *
 * source: https://stackoverflow.com/a/56168225
 * issues: 1. https://stackoverflow.com/questions/51060762/illegalargumentexception-navigation-destination-xxx-is-unknown-to-this-navcontr?page=2&tab=votes#tab-top
 *         2. https://stackoverflow.com/questions/54689361/avoiding-android-navigation-illegalargumentexception-in-navcontroller
 *         3. https://issuetracker.google.com/issues/118975714?pli=1  --> this issue won't fix as is an intended behaviour by androidx team
 *
 * */
fun NavController.navigateSafe(@IdRes currentFragmentId: Int, direction: NavDirections, extras: FragmentNavigator.Extras? = null) {
    if (currentDestination?.id == currentFragmentId) {
        extras?.let { navigate(direction, it) } ?: run { navigate(direction) }
    }
}

fun NavController.navigateSafe(@IdRes currentFragmentId: Int, directionId: Int) {
    if (currentDestination?.id == currentFragmentId) {
        navigate(directionId)
    }
}