package givemesomecoffee.ru.playlistmaker.feature.media.ui.screens.create_playlist

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import givemesomecoffee.ru.playlistmaker.R
import givemesomecoffee.ru.playlistmaker.core.domain.playlist.Playlist
import givemesomecoffee.ru.playlistmaker.core.navigation.Screens
import givemesomecoffee.ru.playlistmaker.core.presentation.utils.dpToPx
import givemesomecoffee.ru.playlistmaker.core.presentation.utils.showToast
import givemesomecoffee.ru.playlistmaker.databinding.FragmentCreatePlaylistBinding
import givemesomecoffee.ru.playlistmaker.feature.main.hideBottomNavigation
import givemesomecoffee.ru.playlistmaker.feature.main.setToolbarTitle
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream

class PlayListCreateFragment : Fragment(R.layout.fragment_create_playlist) {

    private val viewModel by viewModel<PlayListCreateViewModel>()
    private val binding: FragmentCreatePlaylistBinding by viewBinding()
    private var hasData: Boolean = false
    private lateinit var args: Screens.EditPlayListArgs

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                viewModel.onImageSelected(uri)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<Screens.EditPlayListArgs>(Screens.EditPlayListArgs.TYPE)?.let {
            args = it
            val titleRes = when (it) {
                Screens.EditPlayListArgs.Create -> R.string.playlist_create_toolbar
                is Screens.EditPlayListArgs.Edit -> R.string.playlist_edit_toolbar
            }
            setToolbarTitle(resources.getString(titleRes))
            if (it is Screens.EditPlayListArgs.Create) {
                configurePopUp()
            }
            viewModel.obtainArguments(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideBottomNavigation()
        val confirmRes = if(args is Screens.EditPlayListArgs.Create){
            R.string.create
        } else {
            R.string.playlist_edit_confirm
        }

        binding.createPlaylist.setText(confirmRes)

        binding.playlistName.editText?.doOnTextChanged { text, _, _, _ ->
            viewModel.updateName(text?.toString().orEmpty())
        }
        binding.playlistDescription.editText?.doOnTextChanged { text, _, _, _ ->
            viewModel.updateDescription(text?.toString().orEmpty())
        }
        viewModel.state.observe(viewLifecycleOwner, ::updateView)
        viewModel.event.observe(viewLifecycleOwner, ::onEvent)
    }

    private fun onEvent(event: CreatePlayListEvents?) {
        if (event == null) return
        when (event) {
            is CreatePlayListEvents.Created -> {
                findNavController().popBackStack()
                showToast(String.format(getString(R.string.playlist_created), event.name))
            }

            CreatePlayListEvents.Edited -> findNavController().popBackStack()
        }
    }

    private fun updateView(state: Playlist) {
        hasData =
            state.name.isNotEmpty() || state.description.isNotEmpty() || state.path?.isNotEmpty() == true
        Glide.with(binding.root)
            .load(state.path)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .transform(CenterCrop(), RoundedCorners(requireContext().dpToPx(8)))
            .placeholder(R.drawable.ic_add_image).into(binding.playlistImage)
        binding.createPlaylist.setOnClickListener {
            val localPath = state.path?.let { it1 -> saveImageToPrivateStorage(it1, state.name) }
            viewModel.createPlayList(localPath)
        }
        binding.playlistImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        if (state.name != binding.playlistName.editText?.text.toString()) {
            view?.findViewById<TextInputLayout>(R.id.playlist_name)?.editText?.setText(state.name)
        }
        if (binding.playlistDescription.editText?.text.toString() != state.description) {
            binding.playlistDescription.editText?.setText(state.description)
        }
        binding.createPlaylist.isEnabled = state.name.isNotEmpty()
    }

    private fun saveImageToPrivateStorage(path: String, name: String): String {
        if (path.contains("givemesomecoffee.ru.playlistmaker")) return path
        val filePath =
            File(requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "myalbum")
        if (!filePath.exists()) {
            filePath.mkdirs()
        }
        val file = File(filePath, "${name}.jpg")

        val inputStream = requireActivity().contentResolver.openInputStream(path.toUri())
        val outputStream = FileOutputStream(file)
        BitmapFactory
            .decodeStream(inputStream)
            .compress(Bitmap.CompressFormat.JPEG, 30, outputStream)
        return file.path
    }

    private fun configurePopUp() {
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (hasData) {
                        MaterialAlertDialogBuilder(
                            requireContext(),
                            R.style.AppBottomSheetDialogTheme
                        )
                            .setTitle(R.string.playlist_creation_cancel_title)
                            .setMessage(R.string.playlist_creation_cancel_description)
                            .setNeutralButton(R.string.cancel) { dialog, _ ->
                                dialog.dismiss()
                            }.setPositiveButton(R.string.finish) { _, _ ->
                                findNavController().popBackStack()
                            }.show()
                    } else {
                        findNavController().popBackStack()
                    }
                }
            })
    }
}