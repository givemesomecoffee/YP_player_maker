package givemesomecoffee.ru.playlistmaker.feature.main

import androidx.fragment.app.Fragment

interface NavBarsController {
    fun hideBottomNavigation(hide: Boolean)
    fun setFullScreen(isFullScreen: Boolean)
}

fun Fragment.hideBottomNavigation(hide: Boolean = true){
    (requireActivity() as? NavBarsController)?.hideBottomNavigation(hide)
}

fun Fragment.setFullScreen(isFullScreen: Boolean = false){
    (requireActivity() as? NavBarsController)?.setFullScreen(isFullScreen)
}