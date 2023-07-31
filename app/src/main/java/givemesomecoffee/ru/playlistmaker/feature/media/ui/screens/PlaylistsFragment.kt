package givemesomecoffee.ru.playlistmaker.feature.media.ui.screens

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import givemesomecoffee.ru.playlistmaker.R
import givemesomecoffee.ru.playlistmaker.core.domain.playlist.Playlist
import givemesomecoffee.ru.playlistmaker.core.navigation.Actions
import givemesomecoffee.ru.playlistmaker.core.presentation.widget.PlaylistItemClickListener
import givemesomecoffee.ru.playlistmaker.databinding.FragmentPlaylistsBinding
import givemesomecoffee.ru.playlistmaker.feature.media.ui.screens.create_playlist.widget.PlaylistsAdapter
import givemesomecoffee.ru.playlistmaker.feature.media.ui.widget.GridItemDecorator
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistsFragment : Fragment(R.layout.fragment_playlists), PlaylistItemClickListener {

    private val viewModel by viewModel<PlaylistsViewModel>()
    private val binding: FragmentPlaylistsBinding by viewBinding()

    private val playlistsAdapter = PlaylistsAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.playlistsGrid.apply {
            adapter = playlistsAdapter
            if (itemDecorationCount == 0) {
                addItemDecoration(
                    GridItemDecorator(
                        resources.getDimensionPixelSize(R.dimen.playlist_horizontal_spacing),
                        resources.getDimensionPixelSize(R.dimen.playlist_vertical_spacing)
                    )
                )
            }
        }
        binding.createPlaylist.setOnClickListener {
            val action = Actions.ToCreatePlaylist
            findNavController().navigate(action.id, action.bundle)
        }
        viewModel.state.observe(viewLifecycleOwner, ::updateState)
    }

    override fun onItemClicked(playlist: Playlist) {
        val action = Actions.ToPlayListCard(playlist.id.toString())
        findNavController().navigate(action.id, action.bundle)
    }

    private fun updateState(state: List<Playlist>?) {
        state?.let {
            binding.playlistsGrid.isVisible = it.isNotEmpty()
            binding.placeholder.isVisible = it.isEmpty()
            playlistsAdapter.tracks = it
            playlistsAdapter.notifyDataSetChanged()
        }
    }

    companion object {
        fun newInstance() = PlaylistsFragment().apply {
            arguments = Bundle().apply {
                //
            }
        }
    }
}