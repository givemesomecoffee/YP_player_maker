package givemesomecoffee.ru.playlistmaker.feature.playlist_card.ui

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.core.view.isVisible
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.google.android.material.bottomsheet.BottomSheetBehavior
import givemesomecoffee.ru.playlistmaker.R
import givemesomecoffee.ru.playlistmaker.core.navigation.Actions
import givemesomecoffee.ru.playlistmaker.core.presentation.utils.dpToPx
import givemesomecoffee.ru.playlistmaker.core.presentation.utils.resolveTracksCounter
import givemesomecoffee.ru.playlistmaker.databinding.FragmentPlaylistCardBinding
import givemesomecoffee.ru.playlistmaker.feature.main.setFullScreen
import givemesomecoffee.ru.playlistmaker.feature.search_screen.model.TrackUi
import givemesomecoffee.ru.playlistmaker.feature.search_screen.presentation.widget.ItemClickListener
import givemesomecoffee.ru.playlistmaker.feature.search_screen.presentation.widget.TracksAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class PlaylistCardFragment : Fragment(R.layout.fragment_playlist_card), ItemClickListener {

    private val viewModel by viewModel<PlaylistCardViewModel>()
    private val binding: FragmentPlaylistCardBinding by viewBinding()
    private val adapter = TracksAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString(PLAYLIST_ID)?.let {
            viewModel.sync(it)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tlbPlaylistCard.setNavigationIcon(R.drawable.ic_up_button)
        binding.tlbPlaylistCard.setNavigationOnClickListener { findNavController().popBackStack() }
        setFullScreen(true)
        binding.rvTracks.adapter = adapter
        if (binding.rvTracks.itemDecorationCount == 0) {
            binding.rvTracks.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
                ) {
                    outRect.set(0, requireContext().dpToPx(8), 0, requireContext().dpToPx(8))
                }
            })
        }
/*        BottomSheetBehavior.from(binding.container).apply {
            isFitToContents = false
            halfExpandedRatio = 0.3f
            state = BottomSheetBehavior.STATE_HALF_EXPANDED
        }*/
   binding.ivShare.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                binding.ivShare.viewTreeObserver.removeGlobalOnLayoutListener(this)
                val locations = IntArray(2)
                binding.ivShare.getLocationOnScreen(locations)
                val x = locations[0]
                val y = locations[1]
                val height = resources.displayMetrics.heightPixels - (binding.ivShare.measuredHeight + y) - binding.root.context.dpToPx(24)
                BottomSheetBehavior.from(binding.container).peekHeight = height
            }
        })
        viewModel.state.observe(viewLifecycleOwner, ::updateView)
    }

    override fun onStart() {
        super.onStart()
        val temp = IntArray(2)
        binding.ivShare.getLocationInWindow(temp)
        Log.d("custom", temp[1].toString())
        Log.d("custom", temp[0].toString())
        Log.d("custom",binding.ivShare.marginTop.toString())
    }

    private fun updateView(playlist: PlaylistUi?) {
        playlist?.let {
            adapter.tracks = it.tracksList.map { TrackUi.mapFrom(it) }
            adapter.notifyDataSetChanged()
            val hasImage = !it.path.isNullOrEmpty()
            if (hasImage) {
                Glide.with(binding.root)
                    .load(it.path)
                    .transform(CenterCrop())
                    .placeholder(R.drawable.ic_placeholder)
                    .into(binding.ivPlaylist)
            }
            binding.tvPlaylistTracksCounter.text = it.size.resolveTracksCounter()
            binding.playlistPlaceholder.isVisible = !hasImage
            binding.tvPlaylistDuration.text = resolvePlaylistDuration(it.duration)
            binding.run {
                tvPlaylistName.text = it.name
                tvPlaylistDescription.text = it.description
            }
        }
    }

    private fun resolvePlaylistDuration(millis: Long): String {
        val minutes = (millis / 1000 / 60).toInt()
        val postfix = when (minutes % 10) {
            1 -> "минута"
            2, 3, 4 -> "минуты"
            else -> "минут"
        }
        return "$minutes $postfix"
    }

    companion object {
        const val PLAYLIST_ID = "playlist_id"
    }

    override fun onTrackClicked(track: TrackUi) {
        val action = Actions.ToTrackCard(track.trackId, track.trackSource, track.isFavourite)
        findNavController().navigate(action.id, action.bundle)
    }
}