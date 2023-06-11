package givemesomecoffee.ru.playlistmaker.feature.media.ui.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import givemesomecoffee.ru.playlistmaker.R

class PlaylistsFragment : Fragment(R.layout.fragment_playlists) {

    private val viewModel: PlaylistsViewModel by viewModels()

    companion object {
        fun newInstance() = PlaylistsFragment().apply {
            arguments = Bundle().apply {
                //
            }
        }
    }
}