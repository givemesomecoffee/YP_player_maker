package givemesomecoffee.ru.playlistmaker.feature.playlist_card.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import givemesomecoffee.ru.playlistmaker.R
import givemesomecoffee.ru.playlistmaker.core.presentation.utils.dpToPx
import givemesomecoffee.ru.playlistmaker.core.presentation.utils.resolveTracksCounter
import givemesomecoffee.ru.playlistmaker.databinding.FragmentPlaylistCardBinding
import givemesomecoffee.ru.playlistmaker.feature.main.NavBarsController
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistCardFragment: Fragment(R.layout.fragment_playlist_card) {

    private val viewModel  by viewModel<PlaylistCardViewModel>()
    private val binding: FragmentPlaylistCardBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString(PLAYLIST_ID)?.let {
            viewModel.sync(it)
        }
        (activity as? NavBarsController)?.setFullScreen(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner, ::updateView)
    }

    private fun updateView(playlist: PlaylistUi?) {
        playlist?.let {
            val hasImage = !it.path.isNullOrEmpty()
            if(hasImage) {
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
        Log.d("custom-dur", millis.toString())
        val minutes = (millis/1000/60).toInt()
       val postfix = when(minutes%10){
            1 -> "минута"
            2,3,4 -> "минуты"
            else -> "минут"
        }
        return "$minutes $postfix"
    }

    companion object{
        const val PLAYLIST_ID = "playlist_id"
    }

}