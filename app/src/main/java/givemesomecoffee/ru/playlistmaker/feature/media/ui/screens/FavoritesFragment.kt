package givemesomecoffee.ru.playlistmaker.feature.media.ui.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import givemesomecoffee.ru.playlistmaker.R

class FavoritesFragment : Fragment(R.layout.fragment_favourites) {

    private val viewModel: FavouritesViewModel by viewModels()

    companion object {
        fun newInstance() = FavoritesFragment().apply {
            arguments = Bundle().apply {
                //
            }
        }
    }
}