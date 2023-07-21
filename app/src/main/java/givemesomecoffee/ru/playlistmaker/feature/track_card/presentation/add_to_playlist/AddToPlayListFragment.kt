package givemesomecoffee.ru.playlistmaker.feature.track_card.presentation.add_to_playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import givemesomecoffee.ru.playlistmaker.R
import givemesomecoffee.ru.playlistmaker.core.data.playlist.Playlist
import givemesomecoffee.ru.playlistmaker.core.navigation.Actions
import givemesomecoffee.ru.playlistmaker.core.navigation.Screens
import givemesomecoffee.ru.playlistmaker.core.presentation.utils.showToast
import givemesomecoffee.ru.playlistmaker.core.presentation.widget.PlaylistItemClickListener
import givemesomecoffee.ru.playlistmaker.databinding.FragmentAddToPlaylistBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class AddToPlayListFragment : BottomSheetDialogFragment(), PlaylistItemClickListener {

    private lateinit var binding: FragmentAddToPlaylistBinding
    private val viewModel by viewModel<AddToPlaylistViewModel>()
    private val adapter = PlaylistsAdapter(this)

    override fun getTheme(): Int = R.style.AppBottomSheetDialogTheme

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString(Screens.AddToPlayList.TRACK_ID)?.let {
            viewModel.setTrackId(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddToPlaylistBinding.bind(
            inflater.inflate(
                R.layout.fragment_add_to_playlist,
                container,
                false
            )
        )
        return binding.root.apply {
            viewModel.state.observe(viewLifecycleOwner, ::updateState)
            viewModel.event.observe(viewLifecycleOwner, ::onEvent)
            binding.playlistsGrid.adapter = adapter
            binding.createPlaylist.setOnClickListener {
                val action = Actions.ToCreatePlaylist
                findNavController().navigate(action.id, action.bundle)
            }
        }
    }

    override fun onItemClicked(playlist: Playlist) {
        viewModel.addToPlayList(playlist)
    }

    private fun onEvent(event: AddToPlayListEvents?) {
        event?.let {
            when (event) {
                is AddToPlayListEvents.AlreadyAddedToPlaylist -> {
                    showToast(String.format(getString(R.string.track_already_added_to_playlist, event.name)))
                }
                is AddToPlayListEvents.AddedToPlayList -> {
                    findNavController().popBackStack()
                    showToast(String.format(getString(R.string.track_added_to_playlist), event.name ))
                }
            }
            viewModel.onEventDone()
        }
    }

    private fun updateState(state: List<Playlist>?) {
        state?.let {
            binding.playlistsGrid.isVisible = it.isNotEmpty()
            binding.placeholder.isVisible = it.isEmpty()
            adapter.tracks = it
            adapter.notifyDataSetChanged()
        }
    }
}