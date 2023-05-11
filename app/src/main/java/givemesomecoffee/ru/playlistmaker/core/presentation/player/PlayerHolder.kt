package givemesomecoffee.ru.playlistmaker.core.presentation.player

import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.flow.StateFlow

interface PlayerHolder {
    val playerState: StateFlow<PlayerStateUi>
    fun release()
    fun pausePlayer()
    fun preparePlayer(defaultLifecycleObserver: AppCompatActivity, url: String)
    fun startPlayer()
}