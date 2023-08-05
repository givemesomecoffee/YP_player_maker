package givemesomecoffee.ru.playlistmaker.feature.main

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

interface NavBarsController {
    fun hideBottomNavigation(hide: Boolean)
    fun setFullScreen(isFullScreen: Boolean)

    fun hideToolbar(hide: Boolean)
}

fun Fragment.hideBottomNavigation(hide: Boolean = true) {
    (requireActivity() as? NavBarsController)?.hideBottomNavigation(hide)
}

fun Fragment.setFullScreen(isFullScreen: Boolean = false) {
    (requireActivity() as? NavBarsController)?.apply {
        hideBottomNavigation(isFullScreen)
        hideToolbar(isFullScreen)
    }

}

fun Fragment.setToolbarTitle(text: String){
    (requireActivity() as AppCompatActivity).supportActionBar?.title = text
}