package givemesomecoffee.ru.playlistmaker.feature.track_card.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import givemesomecoffee.ru.playlistmaker.R
import givemesomecoffee.ru.playlistmaker.feature.track_card.model.TrackCardScreenState
import givemesomecoffee.ru.playlistmaker.feature.track_card.presentation.widget.TrackInfoAdapter
import givemesomecoffee.ru.playlistmaker.feature.track_card.presentation.widget.TrackInfoItemDecoration
import givemesomecoffee.ru.playlistmaker.core.navigation.Screens
import givemesomecoffee.ru.playlistmaker.core.presentation.player.PlayerState
import givemesomecoffee.ru.playlistmaker.core.presentation.player.configure
import givemesomecoffee.ru.playlistmaker.core.presentation.utils.dpToPx
import givemesomecoffee.ru.playlistmaker.core.presentation.utils.initSecondaryScreen
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

class TrackCardActivity : AppCompatActivity() {

    private val viewModel: TrackCardViewModel by lazy {
        ViewModelProvider(
            this,
            TrackCardViewModel.getViewModelFactory(application = application)
        )[TrackCardViewModel::class.java]
    }
    private var id: String? = null
    private var trackUrl: String? = null

    private val infoAdapter = TrackInfoAdapter()

    private lateinit var trackProgress: TextView
    private lateinit var play: ImageView

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSecondaryScreen("")
        obtainArguments()
        setContentView(R.layout.activity_track_card)
        initView()
        id?.let { viewModel.sync(it) }
        //viewModel.state.observe(this, ::updateScreen)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest {
                    updateScreen(it)
                }
            }
        }
    }

    private fun onProgressChanged(text: String) {
        trackProgress.text = text
    }

    private fun stateChanged(state: PlayerState) {
        when (state) {
            PlayerState.STATE_PAUSED, PlayerState.STATE_PREPARED -> {
                play.apply {
                    isEnabled = true
                    setImageResource(R.drawable.ic_play)
                    setOnClickListener { viewModel.startPlayer() }
                }
            }
            PlayerState.STATE_PLAYING -> {
                play.apply {
                    isEnabled = true
                    setImageResource(R.drawable.ic_pause)
                    setOnClickListener { viewModel.pausePlayer() }
                }
            }
            else -> {
                play.isEnabled = false
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateScreen(state: TrackCardScreenState) {
        state.data?.let { track ->
            Glide.with(this).load(track.artworkUrl100.replaceAfterLast(URL_DELIMITER, IMAGE_SIZE))
                .fitCenter()
                .transform(RoundedCorners(this.dpToPx(8)))
                .placeholder(R.drawable.ic_placeholder).into(findViewById(R.id.track_image))
            findViewById<TextView>(R.id.track_title).text = track.trackName
            findViewById<TextView>(R.id.track_artist).text = track.artistName
            infoAdapter.info = track.info
            infoAdapter.notifyDataSetChanged()
            state.playerState?.progress?.let { onProgressChanged(it) }
            state.playerState?.state?.let { stateChanged(it) }
        }
    }

    private fun initView() {
        findViewById<RecyclerView>(R.id.track_info).apply {
            adapter = infoAdapter
            layoutManager = LinearLayoutManager(context)

            if (itemDecorationCount == 0) {
                addItemDecoration(
                    TrackInfoItemDecoration(
                        verticalOffset = R.dimen.track_description_vertical_offset,
                        context = context
                    )
                )
            }
        }
        trackProgress = findViewById(R.id.track_progress)
        play = findViewById(R.id.play)
    }

    private fun obtainArguments() {
        id = intent.getStringExtra(Screens.TrackCard.ID_ARG_NAME)
        trackUrl = intent.getStringExtra(Screens.TrackCard.TRACK_URL).also {
            if (it != null) {
                configure(viewModel, it)
            }
        }
    }

    companion object {
        const val URL_DELIMITER = "/"
        const val IMAGE_SIZE = "512x512bb.jpg"

    }
}
