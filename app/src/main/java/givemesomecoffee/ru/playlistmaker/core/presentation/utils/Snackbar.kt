package givemesomecoffee.ru.playlistmaker.core.presentation.utils

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showToast(text: String){
    Toast.makeText(
        requireContext(),
        text,
        Toast.LENGTH_SHORT
    ).show()
}