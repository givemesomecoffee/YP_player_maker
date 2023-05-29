package givemesomecoffee.ru.playlistmaker.core.navigation

import androidx.fragment.app.Fragment

interface TabScreen {
    fun getFragment(): Fragment
    fun getTitleRes(): Int
}