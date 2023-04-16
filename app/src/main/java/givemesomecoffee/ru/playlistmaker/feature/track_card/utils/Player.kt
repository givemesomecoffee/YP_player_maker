package givemesomecoffee.ru.playlistmaker.feature.track_card.utils

import android.app.Activity
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import java.text.SimpleDateFormat
import java.util.*
import kotlin.reflect.KProperty

internal class Player(
    private val listener: PlayerContract
) : PlayerApi {
    private var mediaPlayer = MediaPlayer()
    private var playerState = PlayerState.STATE_DEFAULT
    private val handler = Handler(Looper.getMainLooper())

    private val progressRunnable = Runnable { updateProgress() }

    override fun startPlayer() {
        mediaPlayer.start()
        updateState(PlayerState.STATE_PLAYING)
        handler.postDelayed(progressRunnable, UPDATE_PROGRESS_DELAY)
    }

    override fun pausePlayer() {
        handler.removeCallbacks(progressRunnable)
        updateState(PlayerState.STATE_PAUSED)
        mediaPlayer.pause()
    }

    fun init(activity: AppCompatActivity, playerContractImpl: PlayerContract) {
        activity.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                super.onCreate(owner)
                preparePlayer(activity, playerContractImpl.obtainUrl())
            }

            override fun onPause(owner: LifecycleOwner) {
                pausePlayer()
                super.onPause(owner)
            }

            override fun onDestroy(owner: LifecycleOwner) {
                mediaPlayer.release()
                super.onDestroy(owner)
            }
        })
    }

    private fun updateProgress() {
        listener.onProgressChanged(resolveProgress(false))
        handler.postDelayed(progressRunnable, UPDATE_PROGRESS_DELAY)
    }

    private fun resolveProgress(reset: Boolean): String {
        val progress = 0L.takeIf { reset } ?: mediaPlayer.currentPosition
        return SimpleDateFormat("mm:ss", Locale.getDefault()).format(progress)
    }

    private fun preparePlayer(context: Context, url: String) {
        mediaPlayer.setDataSource(context, Uri.parse(url))
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            updateState(PlayerState.STATE_PREPARED)
            listener.onProgressChanged(resolveProgress(true))
        }
        mediaPlayer.setOnCompletionListener {
            handler.removeCallbacks(progressRunnable)
            updateState(PlayerState.STATE_PREPARED)
            listener.onProgressChanged(resolveProgress(true))
        }
    }

    private fun updateState(state: PlayerState){
        playerState = state
        listener.stateChanged(state)
    }

    override operator fun getValue(trackCardActivity: Activity, property: KProperty<*>): PlayerApi =
        this

    companion object{
        private const val UPDATE_PROGRESS_DELAY = 300L
    }
}