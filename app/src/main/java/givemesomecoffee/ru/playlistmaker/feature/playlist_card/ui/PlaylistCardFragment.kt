package givemesomecoffee.ru.playlistmaker.feature.playlist_card.ui

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
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
        configureMenu()
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
        binding.ivShare.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                binding.ivShare.viewTreeObserver.removeGlobalOnLayoutListener(this)
                val locations = IntArray(2)
                binding.ivShare.getLocationInWindow(locations)
                val x = locations[0]
                val y = locations[1]
                val height =
                    resources.displayMetrics.heightPixels - (binding.ivShare.measuredHeight + y) - binding.root.context.dpToPx(
                        24
                    )
                BottomSheetBehavior.from(binding.container).peekHeight = height
            }
        })
        viewModel.state.observe(viewLifecycleOwner, ::updateView)
    }

    private fun updateView(playlist: PlaylistUi?) {
        playlist?.let {
            updateMenuState(it)
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
            binding.ivShare.setOnClickListener {
                sharePlaylist(playlist.shareText)
            }
            binding.ivMenu.setOnClickListener {
                BottomSheetBehavior.from(binding.menu).state = BottomSheetBehavior.STATE_HALF_EXPANDED
            }
        }
    }

    private fun updateMenuState(playlist: PlaylistUi){
        binding.run {
            tvShareMenu.setOnClickListener { sharePlaylist(playlist.shareText) }
            Glide.with(root)
                .load(playlist.path)
                .transform(CenterCrop(), RoundedCorners(requireContext().dpToPx(8)))
                .placeholder(R.drawable.ic_placeholder).into(ivPlaylistMenu)
            tvPlaylistNameMenu.text = playlist.name
            tvPlaylistDescription.text = playlist.description
            tvDeleteMenu
            tvEditMenu
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

    private fun configureMenu() {
        binding.menu.let { menu ->
            BottomSheetBehavior.from(menu).apply {
                state = BottomSheetBehavior.STATE_HIDDEN
                addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        binding.overlay.isVisible = newState != BottomSheetBehavior.STATE_HIDDEN
                    }

                    override fun onSlide(bottomSheet: View, slideOffset: Float) {
                        // TODO("Not yet implemented")
                    }

                })
            }
        }
    }

    private fun sharePlaylist(text: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    companion object {
        const val PLAYLIST_ID = "playlist_id"
    }

    override fun onTrackClicked(track: TrackUi) {
        val action = Actions.ToTrackCard(track.trackId, track.trackSource, track.isFavourite)
        findNavController().navigate(action.id, action.bundle)
    }

    override fun onLongClick(track: TrackUi): () -> Unit {
        return super.onLongClick(track)
    }
}