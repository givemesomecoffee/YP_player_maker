package givemesomecoffee.ru.playlistmaker.feature.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.switchmaterial.SwitchMaterial
import givemesomecoffee.ru.playlistmaker.R
import givemesomecoffee.ru.playlistmaker.core.presentation.utils.validateEvent
import givemesomecoffee.ru.playlistmaker.core.presentation.view.custom_cell.CustomCell
import givemesomecoffee.ru.playlistmaker.feature.settings.model.SettingsScreenStateUi
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val viewModel by viewModel<SettingsViewModel>()
    private var share: CustomCell? = null
    private var support: CustomCell? = null
    private var agreement: CustomCell? = null

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        view?.findViewById<CustomCell>(R.id.theme_switch)
            ?.findViewById<SwitchMaterial>(R.id.switch_control)
            ?.apply {
                viewModel.state.value?.isDarkTheme?.let {
                    this.isChecked = it
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.sync()
        initView()
        viewModel.state.observe(viewLifecycleOwner, ::updateView)
    }

    private fun updateView(state: SettingsScreenStateUi) {
        view?.findViewById<CustomCell>(R.id.theme_switch)
            ?.findViewById<SwitchMaterial>(R.id.switch_control)
            ?.apply {
                if (this.isChecked != state.isDarkTheme) this.isChecked = state.isDarkTheme
            }
    }

    private fun initView() {
        view?.apply {
            share = findViewById(R.id.share)
            support = findViewById(R.id.support)
            agreement = findViewById(R.id.user_agreement)
            findViewById<CustomCell>(R.id.theme_switch).findViewById<SwitchMaterial>(R.id.switch_control)
                ?.apply {
                    setOnClickListener {
                        viewModel.switchTheme()
                    }
                }
            share?.setOnClickListener {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, getString(R.string.share_app_url))
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }
            support?.setOnClickListener {
                composeEmail(
                    subject = getString(R.string.support_email_header),
                    body = getString(R.string.support_email_body)
                )
            }
            agreement?.setOnClickListener {
                openWebPage(getString(R.string.user_agreement_url))
            }
        }
    }

    private fun openWebPage(url: String) {
        val webpage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        requireActivity().validateEvent(intent)
    }

    private fun composeEmail(subject: String, body: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, listOf(getString(R.string.support_email)).toTypedArray())
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
        }
        requireActivity().validateEvent(intent)
    }
}