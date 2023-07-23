package givemesomecoffee.ru.playlistmaker.feature.track_card.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import givemesomecoffee.ru.playlistmaker.R
import givemesomecoffee.ru.playlistmaker.core.navigation.Actions
import givemesomecoffee.ru.playlistmaker.core.navigation.Screens.TrackCard.ID_ARG_NAME
import givemesomecoffee.ru.playlistmaker.core.navigation.Screens.TrackCard.IS_FAVOURITE
import givemesomecoffee.ru.playlistmaker.core.navigation.Screens.TrackCard.TRACK_URL
import givemesomecoffee.ru.playlistmaker.core.presentation.player.PlayerState
import givemesomecoffee.ru.playlistmaker.core.presentation.player.configure
import givemesomecoffee.ru.playlistmaker.core.presentation.utils.dpToPx
import givemesomecoffee.ru.playlistmaker.databinding.ActivityTrackCardBinding
import givemesomecoffee.ru.playlistmaker.feature.track_card.model.TrackCardScreenState
import givemesomecoffee.ru.playlistmaker.feature.track_card.presentation.widget.TrackInfoAdapter
import givemesomecoffee.ru.playlistmaker.feature.track_card.presentation.widget.TrackInfoItemDecoration
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class TrackCardFragment : Fragment(R.layout.activity_track_card) {

    private val viewModel by viewModel<TrackCardViewModel>()
    private val infoAdapter = TrackInfoAdapter()
    private lateinit var trackProgress: TextView
    private lateinit var play: ImageView

    private val binding: ActivityTrackCardBinding by viewBinding()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        obtainArguments()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
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
            view?.apply {
                Glide.with(this)
                    .load(track.artworkUrl100.replaceAfterLast(URL_DELIMITER, IMAGE_SIZE))
                    .transform(CenterCrop(), RoundedCorners(requireContext().dpToPx(8)))
                    .placeholder(R.drawable.ic_placeholder).into(findViewById(R.id.track_image))
                findViewById<TextView>(R.id.track_title).text = track.trackName
                findViewById<TextView>(R.id.track_artist).text = track.artistName
                infoAdapter.info = track.info
                infoAdapter.notifyDataSetChanged()
                state.playerState?.progress?.let { onProgressChanged(it) }
                state.playerState?.state?.let { stateChanged(it) }
                binding.like.setOnClickListener {
                    track.favouriteButtonUi.callback()
                }
                binding.likeIcon.setImageResource(track.favouriteButtonUi.icon)
                binding.add.setOnClickListener {
                    val action = Actions.AddToPlayList(track.trackId)
                    findNavController().navigate(action.id, action.bundle)
                }
            }
        }
    }

    private fun initView() {
        view?.apply {
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
    }

    private fun obtainArguments() {
        val isFavourite = arguments?.getBoolean(IS_FAVOURITE, false) ?: false
        arguments?.getString(ID_ARG_NAME)?.let {
            viewModel.sync(it, isFavourite)
        }
        arguments?.getString(TRACK_URL)?.let {
            configure(viewModel, it)
        }
    }

    companion object {
        const val URL_DELIMITER = "/"
        const val IMAGE_SIZE = "512x512bb.jpg"
    }

}
