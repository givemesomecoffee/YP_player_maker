package givemesomecoffee.ru.playlistmaker.core.presentation.player

import android.content.Context
import kotlinx.coroutines.flow.StateFlow

interface PlayerHolder {
    val playerState: StateFlow<PlayerStateUi>
    fun release()
    fun pausePlayer()
    fun preparePlayer(context: Context, url: String)
    fun startPlayer()
}