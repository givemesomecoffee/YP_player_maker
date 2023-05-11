package givemesomecoffee.ru.playlistmaker.core.presentation.player

import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.*

class Player : PlayerHolder {
    private var mediaPlayer = MediaPlayer()
    private val handler = Handler(Looper.getMainLooper())
    val _flow = MutableStateFlow(PlayerStateUi(progress = null, state = PlayerState.STATE_DEFAULT))
    override val playerState: StateFlow<PlayerStateUi> = _flow
    private val progressRunnable = Runnable { updateProgress() }

    override fun startPlayer() {
        mediaPlayer.start()
        handler.postDelayed(progressRunnable, UPDATE_PROGRESS_DELAY)
        updateState(PlayerState.STATE_PLAYING)

    }

    override fun release() {
        mediaPlayer.release()
    }

    override fun pausePlayer() {
        handler.removeCallbacks(progressRunnable)
        updateState(PlayerState.STATE_PAUSED)
        mediaPlayer.pause()
    }

    override fun preparePlayer(defaultLifecycleObserver: AppCompatActivity, url: String) {
        mediaPlayer.setDataSource(defaultLifecycleObserver.applicationContext, Uri.parse(url))
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            updateState(PlayerState.STATE_PREPARED)
            _flow.value = _flow.value.copy(progress = resolveProgress(true))
        }
        mediaPlayer.setOnCompletionListener {
            handler.removeCallbacks(progressRunnable)
            updateState(PlayerState.STATE_PREPARED)
            _flow.value = _flow.value.copy(progress = resolveProgress(true))
        }
    }

    private fun updateProgress() {
        handler.postDelayed(progressRunnable, UPDATE_PROGRESS_DELAY)
        _flow.value = _flow.value.copy(progress = resolveProgress(false))
    }

    private fun resolveProgress(reset: Boolean): String {
        val progress = 0L.takeIf { reset } ?: mediaPlayer.currentPosition
        return SimpleDateFormat("mm:ss", Locale.getDefault()).format(progress)
    }

    private fun updateState(state: PlayerState){
        _flow.value = this.playerState.value.copy(state = state)
    }

    companion object{
        private const val UPDATE_PROGRESS_DELAY = 300L
    }
}