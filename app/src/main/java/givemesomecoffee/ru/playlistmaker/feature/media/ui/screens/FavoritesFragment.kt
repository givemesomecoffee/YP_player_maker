package givemesomecoffee.ru.playlistmaker.feature.media.ui.screens

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import givemesomecoffee.ru.playlistmaker.R
import givemesomecoffee.ru.playlistmaker.core.navigation.Actions
import givemesomecoffee.ru.playlistmaker.core.presentation.utils.dpToPx
import givemesomecoffee.ru.playlistmaker.databinding.FragmentFavouritesBinding
import givemesomecoffee.ru.playlistmaker.feature.search_screen.model.TrackUi
import givemesomecoffee.ru.playlistmaker.feature.search_screen.presentation.widget.ItemClickListener
import givemesomecoffee.ru.playlistmaker.feature.search_screen.presentation.widget.TracksAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment(R.layout.fragment_favourites), ItemClickListener {

    private val viewModel by viewModel<FavouritesViewModel>()

    private val binding: FragmentFavouritesBinding by viewBinding()
    private val adapter = TracksAdapter(this)
    override fun onTrackClicked(track: TrackUi) {
        val action = Actions.ToTrackCard(track.trackId, track.trackSource, track.isFavourite)
        findNavController().navigate(action.id, action.bundle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecycler()
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest(::updateState)
            }
        }
    }

    private fun updateState(tracks: List<TrackUi>?) {
        if (tracks == null) return
        adapter.tracks = tracks
        adapter.notifyDataSetChanged()
        binding.placeholder.invalidate()
        binding.tracksList.isVisible = tracks.isNotEmpty()
        binding.placeholder.isVisible = tracks.isEmpty()

    }

    private fun configureRecycler() {
        binding.run {
            if (tracksList.itemDecorationCount == 0) {
                tracksList.addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(
                        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
                    ) {
                        outRect.set(0, requireContext().dpToPx(8), 0, requireContext().dpToPx(8))
                    }
                })
            }
            tracksList.adapter = adapter
        }
    }

    companion object {
        fun newInstance() = FavoritesFragment().apply {
            arguments = Bundle().apply {
                //
            }
        }
    }


}