package givemesomecoffee.ru.playlistmaker.core.presentation.player

import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel

fun <T> Fragment.configure(playerHandler: T, url: String) where T: ViewModel, T: PlayerHolder {
    lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onCreate(owner: LifecycleOwner) {
            super.onCreate(owner)
            playerHandler.preparePlayer(requireContext(), url)
        }

        override fun onPause(owner: LifecycleOwner) {
            playerHandler.pausePlayer()
            super.onPause(owner)
        }

        override fun onDestroy(owner: LifecycleOwner) {
            playerHandler.release()
            super.onDestroy(owner)
        }
    })
}