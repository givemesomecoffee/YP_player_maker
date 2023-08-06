package givemesomecoffee.ru.playlistmaker.core.presentation.player

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class Player : PlayerHolder {
    private var mediaPlayer = MediaPlayer()
    private val _flow = MutableStateFlow(PlayerStateUi(progress = null, state = PlayerState.STATE_DEFAULT))
    override val playerState: StateFlow<PlayerStateUi> = _flow
    private var progressJob: Job? = null
    private var scope = CoroutineScope(SupervisorJob())
    override fun startPlayer() {
        mediaPlayer.start()
        progressJob = scope.launch {
            while (true){
                updateProgress()
            }
        }
        updateState(PlayerState.STATE_PLAYING)

    }

    override fun release() {
        mediaPlayer.release()
    }

    override fun pausePlayer() {
        progressJob?.cancel()
        updateState(PlayerState.STATE_PAUSED)
        mediaPlayer.pause()
    }

    override fun preparePlayer(context: Context, url: String) {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(context, Uri.parse(url))
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            mediaPlayer.seekTo(0)
            updateState(PlayerState.STATE_PREPARED)
            _flow.value = _flow.value.copy(progress = resolveProgress(true))
        }
        mediaPlayer.setOnCompletionListener {
            progressJob?.cancel()
            updateState(PlayerState.STATE_PREPARED)
            _flow.value = _flow.value.copy(progress = resolveProgress(true))
        }

    }

    private suspend fun updateProgress() {
        delay(UPDATE_PROGRESS_DELAY)
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